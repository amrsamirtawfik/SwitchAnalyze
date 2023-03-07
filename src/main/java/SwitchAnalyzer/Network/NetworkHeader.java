package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

import java.net.Inet4Address;

public abstract class NetworkHeader extends Header
{
    Inet4Address srcIPADDR ;
    Inet4Address dstIPPADDR ;

    public NetworkHeader(Inet4Address srcIPADDR, Inet4Address dstIPPADDR) {
        this.srcIPADDR = srcIPADDR;
        this.dstIPPADDR = dstIPPADDR;
    }
}
