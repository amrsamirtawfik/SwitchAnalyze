package SwitchAnalyzer.Machines;

import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;
import java.util.ArrayList;

public class MasterOfHPC {
    public  ArrayList<MachineNode> childNodes = new ArrayList<>();
    public HPC_INFO hpcInfo;
    public MacAddress HPCMacAddr;
    public Inet4Address HPCIp;

    public MasterOfHPC(int HPCID) { hpcInfo = new HPC_INFO(HPCID); }
    public int getNoOfChilNodes() { return childNodes.size(); }
    public int getHPCID() { return hpcInfo.HPCID; }
}
