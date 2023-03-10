package SwitchAnalyzer.Network;
import org.pcap4j.packet.Packet;

public class NormalSender extends Sender implements Runnable
{
    public NormalSender(Packet packet , long numPackets)
    {
        super(packet , numPackets);
    }

    @Override
    public void send()
    {
        try
        {
            for (int i = 0; i < numPackets; i++)
            {
                PCAP.handle.sendPacket(packet);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in sending");
            e.printStackTrace();
        }
    }
    @Override
    public void run()
    {
        send();
    }
}
