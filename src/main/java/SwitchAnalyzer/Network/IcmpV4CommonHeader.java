package SwitchAnalyzer.Network;

import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.IcmpV4Code;
import org.pcap4j.packet.namednumber.IcmpV4Type;

public class IcmpV4CommonHeader extends NetworkHeader
{
    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        IcmpV4CommonPacket.Builder icmpV4CommonBuilder = new IcmpV4CommonPacket.Builder();
        icmpV4CommonBuilder
                .type(IcmpV4Type.ECHO)
                .code(IcmpV4Code.NO_CODE)
                .payloadBuilder(prevBuilder)
                .correctChecksumAtBuild(true);
        return icmpV4CommonBuilder;
    }
}
