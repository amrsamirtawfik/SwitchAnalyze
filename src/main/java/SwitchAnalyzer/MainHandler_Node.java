package SwitchAnalyzer;

import SwitchAnalyzer.Commands.ICommandNode;
import SwitchAnalyzer.Commands.ProcessCmd;
import SwitchAnalyzer.Commands.RetrieveCmd_Node;
import SwitchAnalyzer.Commands.StartRunCommand_Node;
import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Kafka.Producer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.ErrorDetection.None;
import SwitchAnalyzer.Network.*;
import SwitchAnalyzer.Network.PacketLoss.PacketLossCalculate;
import SwitchAnalyzer.UtilityExecution.IExecutor;
import SwitchAnalyzer.UtilityExecution.RateExecutor;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import SwitchAnalyzer.miscellaneous.SystemMaps;
import SwitchAnalyzer.miscellaneous.Time;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class MainHandler_Node
{

    public static String consumerGroup = "command-consumer-group";
    static GenericConsumer consumer;
    public static MachineNode node;
    public static Producer dataProducer = new Producer(IP.ip1);

    public static void init()
    {
        SystemMaps.initMapsNode();
        node = new MachineNode(0);
        consumer = new GenericConsumer(IP.ip1 + ":" + Ports.port1, consumerGroup);
        consumer.selectTopic(Topics.cmdFromHpcMaster);
        PCAP.initialize();
    }

    public static void main(String[] args)
    {
        init();
        int commandTypeIndex;
        while (true)
        {
            ConsumerRecords<String, String> records = consumer.consume(Time.waitTime);
            for (ConsumerRecord<String, String> record : records)
            {
                String json = record.value();
                commandTypeIndex = Character.getNumericValue(json.charAt(0));
                json = json.replaceFirst("[0-9]*",""); //removing the number indicating the command type using regex
                System.out.println("MainHandlerNode: "+ json);
                ICommandNode command = JSONConverter.fromJSON(json, SystemMaps.commandClassesNode.get(commandTypeIndex));
                //we need to re check mapping ,how to make it global in all masters and MOM or what should we do ?
                if (command.machineID == node.getMachineID())
                {
                    Thread t1 = new Thread(() -> ProcessCmd.processCmd(command));
                    t1.start();
                }
            }
        }
    }
}
