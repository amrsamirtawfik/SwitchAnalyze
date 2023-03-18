package SwitchAnalyzer;

import SwitchAnalyzer.Collectors.Collector;
import SwitchAnalyzer.Collectors.PLossCollectorMaster;
import SwitchAnalyzer.Collectors.RatesCollectorMaster;
import SwitchAnalyzer.Commands.*;
import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Kafka.Producer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.*;
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

public class MainHandler_Master
{
    public static String consumerGroup = "command-consumer-group";
    public static Producer cmdProducer = new Producer(IP.ip1);
    public static Producer dataProducer = new Producer(IP.ip1);
    static GenericConsumer consumer;
    public static MasterOfHPC master;

    public static void init()
    {
        SystemMaps.initMapsMaster();
        master = new MasterOfHPC(0);// needs to be adjusted by setting these values from the config file and setting it children nodes
        master.childNodes.add(new MachineNode(1));
        GlobalVariable.portHpcMap.put(1, master);
        consumer = new GenericConsumer(IP.ip1 + ":" + Ports.port1, consumerGroup);
        consumer.selectTopic(Topics.cmdFromMOM);
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
                ICommandMaster command = JSONConverter.fromJSON(json, SystemMaps.commandClassesMaster.get(commandTypeIndex));
                if (GlobalVariable.portHpcMap.get(command.portID).getHPCID() == master.getHPCID())
                {
                    Thread t1 = new Thread(() -> ProcessCmd.processCmd(command));
                    t1.start();
                }
            }
        }
    }
}


