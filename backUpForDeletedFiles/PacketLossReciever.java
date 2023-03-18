package SwitchAnalyzer.Network.PacketLoss;

import SwitchAnalyzer.Network.PacketGenerator;
import SwitchAnalyzer.Network.PacketSniffer;
import org.pcap4j.packet.Packet;

public class PacketLossReciever implements EchoPacket {
    PacketSniffer packetSniffer;
    public PacketLossReciever(PacketSniffer sniffer)
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
