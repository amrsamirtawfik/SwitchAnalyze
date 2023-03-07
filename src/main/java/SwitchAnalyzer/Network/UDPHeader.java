package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.namednumber.UdpPort;

public class UDPHeader extends TransportHeader
{
    public UDPHeader(short dstPort, short srcPort) {
        super(dstPort, srcPort);
    }

    @Override
    public  Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        UdpPacket.Builder udpBuilder = new UdpPacket.Builder();;
        udpBuilder
                .dstPort(UdpPort.getInstance((short) dstPort))
                .srcPort(UdpPort.getInstance((short) srcPort))
                .payloadBuilder(prevBuilder)
                .correctChecksumAtBuild(addChecksum)
                .correctLengthAtBuild(true);
            return udpBuilder;
    }
}
