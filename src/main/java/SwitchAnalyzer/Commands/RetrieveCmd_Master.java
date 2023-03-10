package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import static SwitchAnalyzer.MainHandler_Master.master;

public class RetrieveCmd_Master extends ICommandMaster{
    GenericProducer producer;


    public RetrieveCmd_Master(int portID)
    {
        this.portID = portID;
    }

    @Override
    public void processCmd()
    {
        producer = new GenericProducer(IP.ip1+":"+ Ports.port1);
        for (MachineNode node : master.childNodes)
        {
            GenCmd(node.getMachineID());
        }
        producer.close();
    }

    @Override
    public void GenCmd(int machineID)
    {
        RetrieveCmd_Node command = new RetrieveCmd_Node();
        String json = JSONConverter.toJSON(command);
        //dont forget to add number at the beginning of the json to indicate the type of the command
        producer.send(Topics.cmdFromHpcMaster, json);
    }
}
