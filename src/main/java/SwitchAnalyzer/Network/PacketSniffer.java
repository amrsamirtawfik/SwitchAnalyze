package SwitchAnalyzer.Network;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.DataLinkType;
import java.net.Inet4Address;


public class PacketSniffer{
    private BpfProgram pr;
    public PacketSniffer(){}
    public PacketSniffer(String s)
    {
        setFilter(s);
    }
    public  void setFilter(String expr)
    {
        try
        {
            pr = Pcaps.compileFilter(2048 ,new DataLinkType(1, "Ethernet"),
                    expr,BpfProgram.BpfCompileMode.NONOPTIMIZE, (Inet4Address) PCAP.nif.getAddresses().get(0).getNetmask());
            PCAP.handle.setFilter(pr);
        }
        catch (Exception e)
        {
            System.out.println("COULDNT SET FILTER");
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
        PacketSniffer sniffer = new PacketSniffer();
        PCAP.initialize();
        for(int i = 0; i < 10; ++i)
            System.out.println(sniffer.readPacket());
    }
}