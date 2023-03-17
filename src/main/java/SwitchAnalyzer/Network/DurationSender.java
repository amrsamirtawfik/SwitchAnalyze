package SwitchAnalyzer.Network;

import org.pcap4j.packet.Packet;

public class DurationSender extends Sender implements Runnable{
    DurationSender(Packet packet,long duration){
        super(packet,0,duration);
    }
    @Override
    public void send() {
        long startTime = System.currentTimeMillis();
        try
        {
            while (System.currentTimeMillis()-startTime <= duration) {
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
    public void run() {
        send();
    }
}
