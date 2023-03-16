package SwitchAnalyzer.Network;
public class TestSendAndRec {
    public static void main(String[] args)
    {
        PCAP.initialize();
        PacketSniffer sniffer = new PacketSniffer();

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

        SendThreadsHandler.addToPacketInfoList(packetInf);

        Runnable myThread1 = () ->
        {
            for (int i = 0; i < 10; ++i)
                System.out.println(sniffer.readPacket());
        };

        Runnable myThread2 = () ->
        {
            SendThreadsHandler.openThreads();
        };

        Thread t1 = new Thread(myThread1);
        Thread t2 = new Thread(myThread2);

        t1.start();
        t2.start();

        try
        {
            t1.join();
            t2.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
