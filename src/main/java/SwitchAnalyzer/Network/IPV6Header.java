package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

import java.net.Inet4Address;

public class IPV6Header extends NetworkHeader
{

    public IPV6Header(Inet4Address srcIPADDR, Inet4Address dstIPPADDR) {
        super(srcIPADDR, dstIPPADDR);
    }

    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        return prevBuilder;
    }


}
