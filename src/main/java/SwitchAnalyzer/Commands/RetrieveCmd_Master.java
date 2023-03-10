package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import static SwitchAnalyzer.MainHandler_Master.master;

public class RetrieveCmd_Master implements ICommandMaster{
    GenericProducer producer;
    @Override
    public void processCmd()
    {
        producer = new GenericProducer(IP.ip1+":"+ Ports.port1);
        for (MachineNode node : master.childNodes)
        {
            GenCmd();
        }
        producer.close();
    }

    @Override
    public void GenCmd()
    {
        RetrieveCmd_Node command = new RetrieveCmd_Node();
        String json = JSONConverter.toJSON(command);
        //dont forget to add number at the beginning of the json to indicate the type of the command
        producer.send(Topics.cmdFromHpcMaster, json);
    }
}
