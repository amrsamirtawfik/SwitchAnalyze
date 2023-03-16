package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.MainHandler_Master;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import static SwitchAnalyzer.MainHandler_Master.master;

public class StartRunCommand_Master extends ICommandMaster
{
    public SwitchPortConfig portConfig;

    public StartRunCommand_Master(int portID , SwitchPortConfig portConfig)
    {
        this.portID = portID;
        this.portConfig = portConfig;
    }

    @Override
    public void processCmd()
    {
        for (MachineNode node : master.childNodes)
        {
            GenCmd(node.getMachineID());
        }
    }

    @Override
    public void GenCmd(int id)
    {
        String json = JSONConverter.toJSON(new StartRunCommand_Node(portConfig , id));
        json = "0"+json;
        MainHandler_Master.cmdProducer.produce(json,Topics.cmdFromHpcMaster);
        MainHandler_Master.cmdProducer.flush();
    }
}
