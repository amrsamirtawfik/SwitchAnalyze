package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.namednumber.TcpPort;

import java.security.PublicKey;

public class TCPHeader extends TransportHeader
{
    public TCPHeader()
    {

    }

    public TCPHeader(short dstPort, short srcPort) {
        super(dstPort, srcPort);
    }
    @Override
    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        TcpPacket.Builder tcpBuilder = new TcpPacket.Builder();
        tcpBuilder.srcPort(new TcpPort(srcPort, "SRC_PORT_NUMBER"))
                .dstPort(new TcpPort(dstPort, "DEST_PORT_NUMBER"))
                .sequenceNumber(0)
                .acknowledgmentNumber(1)
                .dataOffset((byte) 5)//assume header is 20 byte and the function takes num of words
                .reserved((byte) 0)
                .urg(false)//urgent packet to inform rec that this packet is urgent
                .ack(false)//??
                .psh(false)
                .rst(false)
                .syn(true)//it must be set for first frame only
                .fin(false)
                .window((short) 256)//?? random number for now
                .urgentPointer((short) 0)
                .payloadBuilder(prevBuilder);

        return tcpBuilder;
    }


    }


