package SwitchAnalyzer.Machines;

import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;

public class MachineNode {
    private int MachineID;
    private float rate;
    private MacAddress nodeMacAddress;
    private Inet4Address nodeIp;

    public MachineNode(int machineID, float rate) {
        MachineID = machineID;
        this.rate = rate;
    }
    public int getMachineID() {
        return MachineID;
    }
    public void setMachineID(int machineID) {
        MachineID = machineID;
    }
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }
}

