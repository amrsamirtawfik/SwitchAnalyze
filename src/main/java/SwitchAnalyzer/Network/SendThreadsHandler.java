package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;

import java.util.ArrayList;

public class SendThreadsHandler
{
   private static ArrayList<PacketInfo> packetInfos = new ArrayList<>();

    public static void setPacketInfos (ArrayList<PacketInfo> infos)
    {

        packetInfos = packetInfos;
    }
    public static void addToPacketInfoList(PacketInfo info)
    {
        packetInfos.add(info);

    }

    public static void openThreads()
    {
        ArrayList<Thread> threads = new ArrayList<>();
        for (PacketInfo packetInfo :packetInfos)
        {
            Packet.Builder payloadBuild = Builder.getBuilder(packetInfo.payloadBuilder, new UnknownPacket.Builder());
            Packet.Builder udpBuild = Builder.getBuilder(packetInfo.transportHeader, payloadBuild);
            Packet.Builder networkBuild = Builder.getBuilder(packetInfo.networkHeader, udpBuild);
            Packet.Builder etherBuild = Builder.getBuilder(packetInfo.dataLinkHeader, networkBuild);
            Packet.Builder CRCBuild =Builder.getBuilder(packetInfo.errorDetectingAlgorithm,etherBuild);
            Packet packet = CRCBuild.build();

            if (packetInfo.transportHeader instanceof TCPHeader)
            {

            }
            else
            {
                NormalSender sender = new NormalSender(packet , packetInfo.numberOfPackets);
                threads.add (new Thread(sender));
                threads.get(threads.size()-1).start();
            }
        }
        for (Thread thread : threads)
        {
            try
            {
                thread.join();
            }
            catch (Exception e)
            {
                System.out.println("Couldn't join threads");
            }
        }
    }

    public static void main(String[] args)
    {
        PCAP.initialize();
        PayloadBuilder payloadBuilder = new PayloadBuilder("Testing");
        UDPHeader udpHeader = new UDPHeader((short) 12345, (short) 54321);
        IPV4Header ipv4Header = null;
        try
        {
            ipv4Header = new IPV4Header(Builder.buildIpV4Address("192.168.1.100"), Builder.buildIpV4Address("192.168.1.101"));
        }
        catch (Exception e)
        {
            System.out.println("couldn't make Ipv4 header");
        }
        EthernetHeader ethernetHeader = new EthernetHeader(Builder.buildMacAddress("00:00:00:00:00:01"), Builder.buildMacAddress("00:00:00:00:00:01"));

        ErrorDetectingAlgorithms CRCbytes=new CRC("CRC");

        PacketInfo packetInf = new PacketInfo(payloadBuilder, udpHeader, ipv4Header, ethernetHeader,CRCbytes);
        addToPacketInfoList(packetInf);
        openThreads();
    }
}
