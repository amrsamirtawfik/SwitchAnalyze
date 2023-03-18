package SwitchAnalyzer.Commands;

import SwitchAnalyzer.miscellaneous.GlobalVariable;

public class StopRetrieveCmd_Node extends ICommandNode
{
    public StopRetrieveCmd_Node(int machineID)
    {
        this.machineID = machineID;
    }

    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = false;
    }
}
