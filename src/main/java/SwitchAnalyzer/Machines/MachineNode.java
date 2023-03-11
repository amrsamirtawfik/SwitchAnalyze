package SwitchAnalyzer.Machines;

import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;

/**
 * any information thats not needed to be put into kafka but is related to the machine node should be here
 */
public class MachineNode {
    public MachineInfo machineInfo;
    private int MachineID;
    private float sendRate; //rate from command
    private MacAddress nodeMacAddress;
    private Inet4Address nodeIp;

    public int getPacketLoss() {
        return packetLoss;
    }

    private int packetLoss;

    public MachineNode(int machineID)
    {
        MachineID = machineID;
    }
    public int getMachineID() {
        return MachineID;
    }
    public void setMachineID(int machineID) {
        MachineID = machineID;
    }
    public float getRate() {
        return sendRate;
    }
    public void setRate(float rate) {
        this.sendRate = rate;
    }
}

