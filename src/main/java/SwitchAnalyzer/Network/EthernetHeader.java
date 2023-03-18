package SwitchAnalyzer.Network;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;

public class EthernetHeader extends DataLinkHeader
{
    public EthernetHeader(){}
    public EthernetHeader(MacAddress srcMac, MacAddress dstMac) { super(srcMac, dstMac); }

    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .srcAddr(srcMac)
                .dstAddr(dstMac)
                .payloadBuilder(prevBuilder)
                .paddingAtBuild(true);

        if (prevBuilder instanceof IpV4Packet.Builder)
            etherBuilder.type(EtherType.IPV4);
        else
            etherBuilder.type(EtherType.IPV6);

        return etherBuilder;
    }
}
