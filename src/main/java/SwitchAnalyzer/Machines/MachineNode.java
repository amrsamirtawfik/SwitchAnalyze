package SwitchAnalyzer.Machines;

import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;

/**
 * any information thats not needed to be put into kafka but is related to the machine node should be here
 */
public class MachineNode
{
    public MachineInfo machineInfo;
    public MacAddress nodeMacAddress;
    public Inet4Address nodeIp;

    public MachineNode(int id) { machineInfo = new MachineInfo(id); }
    public int getMachineID() { return machineInfo.machineID; }
}

