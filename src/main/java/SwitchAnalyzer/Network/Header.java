package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

public abstract class Header
{
    public abstract Packet.Builder buildHeader(Packet.Builder prevBuilder);
}
