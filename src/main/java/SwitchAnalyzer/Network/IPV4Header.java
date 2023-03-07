package SwitchAnalyzer.Network;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.IpVersion;

import java.net.Inet4Address;
import java.util.Random;

public class IPV4Header extends NetworkHeader
{
    public IPV4Header(Inet4Address srcIPADDR, Inet4Address dstIPPADDR) {
        super(srcIPADDR, dstIPPADDR);
    }

    @Override
    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        IpV4Packet.Builder ipBuilder = new IpV4Packet.Builder();
        ipBuilder
                .version(IpVersion.IPV4)
                .tos((IpV4Packet.IpV4Tos) () -> (byte) 0)
                .identification((short) new Random().nextInt())
                .ttl((byte) 100)
                .srcAddr(srcIPADDR)
                .dstAddr(dstIPPADDR)
                .payloadBuilder(prevBuilder)
                .correctChecksumAtBuild(true)
                .correctLengthAtBuild(true);

        if (prevBuilder instanceof UdpPacket.Builder)
            ipBuilder.protocol(IpNumber.UDP);
        else
            ipBuilder.protocol(IpNumber.TCP);

        return ipBuilder;
    }
}
