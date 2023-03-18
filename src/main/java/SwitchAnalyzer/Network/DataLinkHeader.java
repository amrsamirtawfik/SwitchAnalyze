package SwitchAnalyzer.Network;

import org.pcap4j.util.MacAddress;

public abstract class DataLinkHeader extends Header

{
    public MacAddress srcMac;
    public MacAddress dstMac;

    public DataLinkHeader(){}
    public DataLinkHeader(MacAddress srcMac, MacAddress dstMac)
    {
        this.srcMac = srcMac;
        this.dstMac = dstMac;
    }
}
