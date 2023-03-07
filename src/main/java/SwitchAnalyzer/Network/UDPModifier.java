package SwitchAnalyzer.Network;

import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.namednumber.UdpPort;

public class UDPModifier {
    private static UdpPacket.Builder udpBuilder;
    public static void modifiyDstPort(Packet.Builder builder , short dstPort)
    {
        udpBuilder = builder.get(UdpPacket.Builder.class);
        udpBuilder.dstPort(UdpPort.getInstance(dstPort));
    }
}
