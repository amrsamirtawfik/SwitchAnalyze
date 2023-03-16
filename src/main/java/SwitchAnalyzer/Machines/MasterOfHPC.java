package SwitchAnalyzer.Machines;

import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;
import java.util.ArrayList;

public class MasterOfHPC {
    public  ArrayList<MachineNode> childNodes = new ArrayList<MachineNode>();
    public HPC_INFO hpcInfo;
    //TODO: will be used when constructing packets to be sent
    private MacAddress HPCMacAddr;
    private Inet4Address HPCIp;
    public MasterOfHPC(int HPCID) {
        hpcInfo = new HPC_INFO(HPCID);
    }
    public int getNoOfChilNodes() {
        return childNodes.size();
    }
    public int getHPCID() {
        return hpcInfo.HPCID;
    }

}