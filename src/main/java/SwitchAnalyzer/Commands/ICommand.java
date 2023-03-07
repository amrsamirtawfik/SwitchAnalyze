package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;

import java.util.ArrayList;

public interface ICommand {
    ArrayList<SwitchPort> ports = new ArrayList<>();
    public void processCmd();
    public void GenCmd();
}
