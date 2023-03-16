package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Collectors.MasterConsumer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.MainHandler_Master;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import static SwitchAnalyzer.MainHandler_Master.master;

public class StopRetrieveCmd_Master extends ICommandMaster
{

    public StopRetrieveCmd_Master(int portID)
    {
        this.portID = portID;
    }

    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = false;
        for (MachineNode node : master.childNodes)
        {
            GenCmd(node.getMachineID());
        }
        clearMasterConsumer();
    }

    public void GenCmd(int machineID)
    {
        String json = JSONConverter.toJSON(new StopRetrieveCmd_Node(machineID));
        json = "2" + json;
        MainHandler_Master.cmdProducer.produce(json, Topics.cmdFromMOM);
        MainHandler_Master.cmdProducer.flush();
    }

    public void clearMasterConsumer()
    {
        MasterConsumer.clearResults();
        MasterConsumer.clearCollectors();
    }
}
