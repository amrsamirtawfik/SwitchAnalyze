package SwitchAnalyzer.Commands;

import SwitchAnalyzer.miscellaneous.GlobalVariable;

public class RetrieveCmd_Node implements ICommandNode{

    @Override
    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = true;
    }
}
