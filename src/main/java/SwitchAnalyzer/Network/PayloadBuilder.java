package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;

public class PayloadBuilder extends Header{

    public String payload;
    public PayloadBuilder(String payload)
    {
        this.payload = payload;
    }
    @Override
    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        UnknownPacket.Builder unknownBuilder = new UnknownPacket.Builder();
        unknownBuilder.rawData(payload.getBytes());
        return unknownBuilder;
    }
}
