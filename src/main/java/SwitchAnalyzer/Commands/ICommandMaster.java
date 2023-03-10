package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.MasterHPC;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static SwitchAnalyzer.MasterHPC.consumerGroup;

public interface ICommandMaster extends ICommand
{
    public void GenCmd();
}
