package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;

import java.util.ArrayList;

public interface ICommandMOM extends ICommand
{
    ArrayList<SwitchPort> ports= new ArrayList<>();
    public void GenCmd(SwitchPort port);
}
