package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.MacAddress;

public abstract class DataLinkHeader extends Header

{
    MacAddress srcMac;
    MacAddress dstMac;

    public DataLinkHeader(MacAddress srcMac, MacAddress dstMac)
    {
        this.srcMac = srcMac;
        this.dstMac = dstMac;
    }


}
