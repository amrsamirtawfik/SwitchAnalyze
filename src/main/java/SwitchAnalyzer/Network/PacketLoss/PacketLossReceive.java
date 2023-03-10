package SwitchAnalyzer.Network.PacketLoss;

import SwitchAnalyzer.Network.PacketSniffer;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.packet.Packet;

public class PacketLossReceive
{
    PacketSniffer sniffer;
    PacketLossReceive(PacketSniffer s1)
    {
        sniffer = s1;
    }

    int receivedPackets = 0;

    public void receivePacket()
    {
        Packet p;
        p = sniffer.readPacket();
        receivedPackets++;
    }
}
