package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Collectors.MasterConsumer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.MainHandler_Master;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.ProduceData_Master;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import static SwitchAnalyzer.MainHandler_Master.master;

public class RetrieveCmd_Master extends ICommandMaster{
    GenericProducer producer;
    public static Thread listeningThread;

    public RetrieveCmd_Master(int portID)
    {
        this.portID = portID;
    }

    @Override
    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = true;
        producer = new GenericProducer(IP.ip1+":"+ Ports.port1);
        for (MachineNode node : master.childNodes)
        {
            GenCmd(node.getMachineID());
        }
        producer.close();
        MasterConsumer.addCollector(MainHandler_Master.collectors.get(0));
        MasterConsumer.addCollector(MainHandler_Master.collectors.get(1));
        listeningThread = new Thread (() ->
        {
           while(GlobalVariable.retrieveDataFromNode)
           {
               ProduceData_Master.produceData();
           }
        });
    }

    @Override
    public void GenCmd(int machineID)
    {
        RetrieveCmd_Node command = new RetrieveCmd_Node(machineID);
        String json = JSONConverter.toJSON(command);
        //dont forget to add number at the beginning of the json to indicate the type of the command
        json = "1"+json;
        producer.send(Topics.cmdFromHpcMaster, json);
    }
}
