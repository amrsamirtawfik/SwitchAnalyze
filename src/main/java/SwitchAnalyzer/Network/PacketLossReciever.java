package SwitchAnalyzer.Network;

import org.pcap4j.packet.Packet;

public class PacketLossReciever implements EchoPacket{
    PacketSniffer packetSniffer;
    PacketLossReciever(PacketSniffer sniffer)
    {
        this.packetSniffer = sniffer;
    }
    public void echoPacket()
    {
        Packet packet;
        try
        {
            packet = packetSniffer.readPacket();
            Packet.Builder builder = packet.getBuilder();
            PacketGenerator.sendPacket(packet);
        }
        catch(Exception e)
        {
            System.out.println("couldn't read packets");
            e.printStackTrace();
        }
    }
}
