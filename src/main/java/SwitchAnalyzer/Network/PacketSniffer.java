package SwitchAnalyzer.Network;

import SwitchAnalyzer.MainHandler_Node;
import SwitchAnalyzer.Network.ErrorDetection.CRC;
import SwitchAnalyzer.Sockets.PacketInfoGui;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.DataLinkType;

import javax.crypto.Mac;
import java.net.Inet4Address;
import java.util.ArrayList;


public class PacketSniffer
{
    private static final ArrayList<PacketInfoGui> packetInfoGuis = new ArrayList<>();

    public PacketSniffer(){}

    public static void addToPacketInfoList(PacketInfoGui packetInfoGui) { packetInfoGuis.add(packetInfoGui); }

    public  void setFilter(String expr, PcapHandle handle)
    {
        try
        {
            BpfProgram pr = Pcaps.compileFilter(2048, new DataLinkType(1, "Ethernet"),
                    expr, BpfProgram.BpfCompileMode.NONOPTIMIZE, (Inet4Address) PCAP.nif.getAddresses().get(0).getNetmask());
            handle.setFilter(pr);
        }
        catch (Exception e) { System.out.println("COULDN'T SET FILTER"); }
    }

    public static void openThreads()
    {
        for (PacketInfoGui packetInfo : packetInfoGuis)
        {
            Thread t = new Thread (() -> startRead(packetInfo));
        }
    }

    public static void startRead(PacketInfoGui packetInfoGui)
    {
        for(int i = 0; i < packetInfoGui.numberOfPackets; ++i)
        {
            PacketSniffer packetSniffer = new PacketSniffer();
            PcapHandle pcapHandle = PCAP.createHandle();
            packetSniffer.setFilter(packetSniffer.getStringFromPacketInfo(packetInfoGui), pcapHandle);
            Packet p = packetSniffer.readPacket();
            System.out.println(p);
        }
    }

    public String getStringFromPacketInfo(PacketInfoGui packetInfoGui)
    {
        StringBuilder filter = new StringBuilder();
        filter.append("inbound and ");
        if (packetInfoGui.networkHeader.equals("ipv4"))
            filter.append("ip and");
        filter.append(packetInfoGui.transportHeader);
        filter.append(" port 12345 and");
        filter.append("ether dst ").append(MainHandler_Node.node.nodeMacAddress.toString());
        System.out.println(filter);
        return filter.toString();
    }

    public Packet readPacket()
    {
        Packet p = null;
        try
        {
            p = PCAP.handle.getNextPacket();
            while (p == null) { p = PCAP.handle.getNextPacket(); }
        }
        catch (NullPointerException e) { System.out.println("Packet is NULL"); }
        catch (Exception e) { System.out.println("ERROR in Capturing packet"); }
        return p;
    }
}
