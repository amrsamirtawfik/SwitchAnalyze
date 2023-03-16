package SwitchAnalyzer.Network;

import org.pcap4j.packet.IcmpV4EchoPacket;
import org.pcap4j.packet.Packet;

public class IcmpEchoHeader extends NetworkHeader
{
    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        IcmpV4EchoPacket.Builder echoBuilder = new IcmpV4EchoPacket.Builder();
        echoBuilder
                .identifier((short) 1)
                .payloadBuilder(prevBuilder);
        return echoBuilder;
    }
}
