package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

public abstract class Sender
{
    Packet packet;

    long numPackets;

    public Sender(Packet packet , long numPackets)
    {
        this.packet = packet;
        this.numPackets = numPackets;
    }

    public abstract void send ();


}
