package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ICommandMaster implements ICommand
{
    public int portID;
    public abstract void GenCmd(int machineID);
}
