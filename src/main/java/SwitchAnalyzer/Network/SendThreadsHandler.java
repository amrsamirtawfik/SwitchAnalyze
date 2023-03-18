package SwitchAnalyzer.Network;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.MainHandler_Node;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;

import java.util.ArrayList;

public class SendThreadsHandler
{
    private static ArrayList<PacketInfo> packetInfos = new ArrayList<>();

    public static void setPacketInfos (ArrayList<PacketInfo> infos) { packetInfos = packetInfos; }
    public static void addToPacketInfoList(PacketInfo info) { packetInfos.add(info); }

    public static void sendToSelectedPort(int toPort)
    {
        ArrayList<Thread> threads = new ArrayList<>();
        MasterOfHPC selectedHPC = PortSelector.selectForPort(toPort);
        for (MachineNode node : selectedHPC.childNodes)
        {
            Thread t = new Thread(()-> openThreads(toPort , node));
            threads.add(t);
            t.start();
        }
        for (Thread thread : threads )
        {
            try { thread.join(); }
            catch (Exception ignored){}
        }
        clearPacketInfos();
    }

    public static void openThreads(int toPort , MachineNode node)
    {
        ArrayList<Thread> threads = new ArrayList<>();
        for (PacketInfo packetInfo :packetInfos)
        {

            packetInfo.transportHeader.srcPort =12345;
            packetInfo.transportHeader.dstPort = (short)54321;
            try
            {
                packetInfo.networkHeader.srcIPADDR = MainHandler_Node.node.nodeIp;
                packetInfo.networkHeader.dstIPPADDR = node.nodeIp;
                packetInfo.dataLinkHeader.srcMac = MainHandler_Node.node.nodeMacAddress;
                packetInfo.dataLinkHeader.dstMac = node.nodeMacAddress;
            }
            catch (Exception ignored) {}

            Packet.Builder payloadBuild = Builder.getBuilder(packetInfo.payloadBuilder, new UnknownPacket.Builder());
            Packet.Builder udpBuild = Builder.getBuilder(packetInfo.transportHeader, payloadBuild);
            Packet.Builder networkBuild = Builder.getBuilder(packetInfo.networkHeader, udpBuild);
            Packet.Builder etherBuild = Builder.getBuilder(packetInfo.dataLinkHeader, networkBuild);
            Packet packet = etherBuild.build();

            NormalSender sender = new NormalSender(packet , packetInfo.numberOfPackets /PortSelector.selectForPort(toPort).childNodes.size());
            threads.add (new Thread(sender));
            threads.get(threads.size()-1).start();
        }

        for (Thread thread : threads)
        {
            try { thread.join(); }
            catch (Exception e) { System.out.println("Couldn't join threads"); }
        }
    }

    public static void clearPacketInfos() { packetInfos.clear(); }

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

        PacketInfo packetInf = new PacketInfo(payloadBuilder, udpHeader, ipv4Header, ethernetHeader);
        addToPacketInfoList(packetInf);
    }
}
