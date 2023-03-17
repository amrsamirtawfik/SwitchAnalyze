package SwitchAnalyzer.Network;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.Packet;

public class NormalSender extends Sender implements Runnable
{
    public NormalSender(Packet packet , long numPackets,long duration,long sendingRate)
    {
        super( packet,numPackets,duration,sendingRate);
    }

    @Override
    public void send()
    {
        try
        {
            if(duration > 0){
                durationSending();
            }else{
                rateSending();
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

    public void durationSending() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= duration) {
            try {
                PCAP.handle.sendPacket(packet);
            } catch (PcapNativeException e) {
                throw new RuntimeException(e);
            } catch (NotOpenException e) {
                throw new RuntimeException(e);
            }
        }
    }

        public void rateSending(){

            long differenceTime=1000/sendingRate;//in milli

            for (int i = 0; i < numPackets; i++)
            {
                try {
                    long startTime=System.currentTimeMillis();
                    PCAP.handle.sendPacket(packet);
                long endTime =System.currentTimeMillis();
                if(sendingRate > 0)
                    Thread.sleep(differenceTime-(endTime-startTime));

                } catch (PcapNativeException e) {
                    throw new RuntimeException(e);
                } catch (NotOpenException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

