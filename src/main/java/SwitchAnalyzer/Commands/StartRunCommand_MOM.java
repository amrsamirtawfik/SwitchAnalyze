package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import java.util.ArrayList;

public class StartRunCommand_MOM implements ICommandMOM
{
    ArrayList<SwitchPort> ports= new ArrayList<>();
    @Override
    public void processCmd()
    {
        for (SwitchPort switchPort : ports)
        {
           GenCmd(switchPort);
        }
    }

    @Override
    public void GenCmd(SwitchPort port)
    {
        StartRunCommand_Master command = new StartRunCommand_Master(port.ID, port.portConfig);
        String json = JSONConverter.toJSON(command);
        //dont forget to add number at the beginning of the json to indicate the type of the command
        GenericProducer producer = new GenericProducer(IP.ip1+":"+ Ports.port1);
        producer.send(Topics.cmdFromMOM, json);
        producer.close();
    }
}
