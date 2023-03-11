package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.namednumber.UdpPort;

public class UDPHeader extends TransportHeader
{
    public UDPHeader(short dstPort, short srcPort) {
        super(dstPort, srcPort);
    }
    public UDPHeader(){
        super();
    };

    @Override
    public  Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        UdpPacket.Builder udpBuilder = new UdpPacket.Builder();
        udpBuilder
                .dstPort(UdpPort.getInstance(dstPort))
                .srcPort(UdpPort.getInstance(srcPort))
                .payloadBuilder(prevBuilder)
                .correctChecksumAtBuild(addChecksum)
                .correctLengthAtBuild(true);
            return udpBuilder;
    }
}
