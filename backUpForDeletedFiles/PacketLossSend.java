package SwitchAnalyzer.Network.PacketLoss;

import SwitchAnalyzer.Network.PacketGenerator;
import SwitchAnalyzer.Network.Ping;
import org.pcap4j.packet.EthernetPacket;

import java.net.UnknownHostException;

public class PacketLossSend extends Ping
{
    public static int windowSize = 1000;
    @Override
    public void ping() throws UnknownHostException
    {
        //set udp or tcp ports by the port that will be assigned for packet loss pinging
        String payload = "1";
        int payloadIntValue;
        EthernetPacket packet;
        for (int i = 0 ; i <windowSize ;i++)
        {
            packet = PacketGenerator.buildPacket();
            PacketGenerator.sendPacket(packet);
            payloadIntValue = Integer.parseInt(payload);
            payloadIntValue++;
            payload = payloadIntValue+"";
        }
    }

    public static void main(String[] args)
    {
        PacketLossSend p1 = new PacketLossSend();
        try
        {
            p1.ping();
        }
        catch (Exception e){}
    }
}
