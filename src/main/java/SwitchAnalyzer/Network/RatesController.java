package SwitchAnalyzer.Network;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.Packet;

import java.net.UnknownHostException;

public class RatesController {
    public static void main(String[] args) throws UnknownHostException, NotOpenException, PcapNativeException, InterruptedException {
        PCAP.initialize();
        Packet p=PacketGenerator.buildPacket();

        long sendingRate=1000;//1 packet per second
        long differenceTime=1000/sendingRate;//in milli
        long totalNumberOfPrackets=1000;
        int i=0;
//        Thread t1 = new Thread(()->{
//            System.out.println(Observer.getRate());
//        });
//        t1.start();

        while(i<totalNumberOfPrackets){
            long startTime=System.currentTimeMillis();
            PCAP.handle.sendPacket(p);
            long endTime =System.currentTimeMillis();
            Thread.sleep(differenceTime-(endTime-startTime));
            i++;
        }
    }


}
