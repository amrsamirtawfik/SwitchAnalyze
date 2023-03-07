package SwitchAnalyzer.Network;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.IllegalRawDataException;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Test {
    public static void main(String[] args)
    {
        PacketSniffer packetSnifferPacketLoss = new PacketSniffer("port 12345");
        PacketLossReciever packetLossReciever = new PacketLossReciever(packetSnifferPacketLoss);
        Runnable packetLossRecieverThread = () ->
        {
            while(true)
            {
                packetLossReciever.echoPacket();
            }
        };
        Thread threadPacketLoss = new Thread(packetLossRecieverThread);
    }
}
