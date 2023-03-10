package SwitchAnalyzer.Commands;

import SwitchAnalyzer.miscellaneous.GlobalVariable;

public class RetrieveCmd_Node extends ICommandNode{

    @Override
    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = true;
    }
}
