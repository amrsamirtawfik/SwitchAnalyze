package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

public abstract class Sender implements Runnable
{
    Packet packet;

    long numPackets;

    long duration;//this time is in milli seconds
    
    long sendingRate;

    public Sender(Packet packet , long numPackets,long duration,long sendingRate)
    {
        this.packet = packet;
        this.numPackets = numPackets;
        this.duration=duration;
        this.sendingRate=sendingRate;
    }



    public abstract void send ();


}
