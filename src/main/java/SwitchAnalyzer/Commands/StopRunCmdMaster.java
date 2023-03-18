package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.MainHandler_Master;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import java.util.ArrayList;

import static SwitchAnalyzer.MainHandler_Master.master;

public class StopRunCmdMaster extends ICommandMaster
{

    public StopRunCmdMaster(int portID) { this.portID = portID; }

    @Override
    public void processCmd()
    {
        for (MachineNode node : master.childNodes) { GenCmd(node.getMachineID()); }
    }

    @Override
    public void GenCmd(int id)
    {
        String json = JSONConverter.toJSON(new StopRunCmd_Node(id));
        json = "3"+json;
        MainHandler_Master.cmdProducer.produce(json, Topics.cmdFromHpcMaster);
        MainHandler_Master.cmdProducer.flush();
    }
}
