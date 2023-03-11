package SwitchAnalyzer.Machines;

import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class MachineNode {
    public MachineInfo machineInfo;
    private int MachineID;
    private float rate;
    private MacAddress nodeMacAddress;
    private Inet4Address nodeIp;

    public int getPacketLoss() {
        return packetLoss;
    }

    private int packetLoss;
            public MachineNode(){
                try {
                    InetAddress ipAddress = InetAddress.getLocalHost();
                    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);
                    byte[] macAddressBytes = networkInterface.getHardwareAddress();
                    if (macAddressBytes != null) {
                        nodeMacAddress =MacAddress.getByAddress(macAddressBytes);
                    }

                    /*
                    get ip address and set it
                     */
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
    public MacAddress getMyMacAddress() {
        return nodeMacAddress;
    }



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

