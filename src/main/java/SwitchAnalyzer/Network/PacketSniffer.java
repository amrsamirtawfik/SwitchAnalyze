package SwitchAnalyzer.Network;

import SwitchAnalyzer.Network.ErrorDetection.CRC;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.DataLinkType;

import java.net.Inet4Address;


public class PacketSniffer
{
    public PacketSniffer(){}
    public PacketSniffer(String s)
    {
        setFilter(s);
    }
    public  void setFilter(String expr)
    {
        try
        {
            BpfProgram pr = Pcaps.compileFilter(2048, new DataLinkType(1, "Ethernet"),
                    expr, BpfProgram.BpfCompileMode.NONOPTIMIZE, (Inet4Address) PCAP.nif.getAddresses().get(0).getNetmask());
            PCAP.handle.setFilter(pr);
        }
        catch (Exception e)
        {
            System.out.println("COULDN'T SET FILTER!!!!!!!!");
        }
    }
    public Packet readPacket()
    {
        Packet p = null;
        try
        {
            p = PCAP.handle.getNextPacket();

            while (p == null)
            {
                p = PCAP.handle.getNextPacket();
            }
        }
        catch (NullPointerException e)
        {
            System.out.println("Packet is NULL");
        }
        catch (Exception e)
        {
            System.out.println("ERROR in Capturing packet");
        }
        return p;
    }
    public static void  main(String [] args)
    {
        PCAP.initialize();
        PacketSniffer sniffer = new PacketSniffer("dst port 12345");
        CRC c = new CRC(false);
        for(int i = 0; i < 10; ++i)
            System.out.println(c.isAlgorithmCorrect(sniffer.readPacket().getRawData()));
    }
}