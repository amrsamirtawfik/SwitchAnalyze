package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.MainHandler_MOM;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
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
        String json = JSONConverter.toJSON(new StartRunCommand_Master(port.ID, port.portConfig));
        json = "0"+json;
        MainHandler_MOM.cmdProducer.produce(json,Topics.cmdFromMOM);
        MainHandler_MOM.cmdProducer.flush();
    }
}
