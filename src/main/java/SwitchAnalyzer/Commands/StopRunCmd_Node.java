package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Network.SendThreadsHandler;

public class StopRunCmd_Node extends ICommandNode
{
    public void processCmd()
    {
        SendThreadsHandler.stopThreads();
    }
}
