package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

public abstract class Sender implements Runnable
{
    Packet packet;

    long numPackets;

    long duration;//this time is in milli seconds

    public Sender(Packet packet , long numPackets,long duration)
    {
        this.packet = packet;
        this.numPackets = numPackets;
        this.duration=duration;
    }
    public Sender(Packet packet , long numPackets)
    {
        this.packet = packet;
        this.numPackets = numPackets;
    }


    public abstract void send ();


}
