package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;

public interface ICommandMOM extends ICommand
{
    void GenCmd(SwitchPort port);
}
