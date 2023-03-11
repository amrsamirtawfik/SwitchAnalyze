package SwitchAnalyzer.Commands;

import SwitchAnalyzer.miscellaneous.GlobalVariable;

public class RetrieveCmd_Node extends ICommandNode{

    public RetrieveCmd_Node(int machineID)
    {
        this.machineID = machineID;
    }

    @Override
    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = true;
    }
}
