package SwitchAnalyzer.Network;

import SwitchAnalyzer.Network.PacketLoss.PacketLossReciever;

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
