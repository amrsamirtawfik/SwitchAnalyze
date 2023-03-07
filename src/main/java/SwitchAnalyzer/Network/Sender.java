package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

import java.security.PublicKey;

public abstract class Sender
{
    Packet packet;

    int numPackets;

    public Sender(Packet packet , int numPackets)
    {
        this.packet = packet;
        this.numPackets = numPackets;
    }

    public abstract void send ();


}
