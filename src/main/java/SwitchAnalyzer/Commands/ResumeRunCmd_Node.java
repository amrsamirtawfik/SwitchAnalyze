package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Network.SendThreadsHandler;

public class ResumeRunCmd_Node extends ICommandNode
{
    public void processCmd()
    {
        SendThreadsHandler.resumeThreads();
    }
}