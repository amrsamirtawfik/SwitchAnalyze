package SwitchAnalyzer.Network.PacketLoss;

import SwitchAnalyzer.Network.*;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.util.MacAddress;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static SwitchAnalyzer.Network.PCAP.nif;

public class PacketLossCalculate {

    public static final int COUNT = 10;
    private static final int READ_TIMEOUT = 10; //IN MS
    private static final int SNAPLEN = 65536;
    public  String strSrcIpAddress;
    public  String strSrcMacAddress;
    public  String strDstIpAddress;
    public  String strDstMacAddress;
    public  String echoData;
    public int recievedPacketCount = 0;
    private PcapHandle handle;
    private PcapHandle sendHandle;
    private final ExecutorService pool = Executors.newSingleThreadExecutor();



    public PacketLossCalculate(){};

    public PacketLossCalculate(String strSrcIpAddress, String strSrcMacAddress,
                               String strDstIpAddress, String strDstMacAddress, MacAddress srcMacAddr, String echoData)
    {
        this.strSrcIpAddress = strSrcIpAddress;
        this.strSrcMacAddress = strSrcMacAddress;
        this.strDstIpAddress = strDstIpAddress;
        this.strDstMacAddress = strDstMacAddress;
        this.echoData = echoData;
    }

    private void init()
    {
        try
        {
            handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
            sendHandle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        }
        catch (Exception ignored){}
    }
    public void startEchoLisitner() throws PcapNativeException
    {
        try
        {
            handle.setFilter("icmp and ether dst " + Pcaps.toBpfString(Builder.buildMacAddress(strSrcMacAddress)), BpfProgram.BpfCompileMode.OPTIMIZE);
            PacketListener listener = new PacketListener()
            {
                @Override
                public void gotPacket(PcapPacket packet)
                {
                    recievedPacketCount++;
                }
            };
            PacketLossCalculate.Task t = new PacketLossCalculate.Task(handle, listener);
            pool.execute(t);
        }
        catch (Exception ignored){}
    }

    public void startPacketLossTest() throws PcapNativeException
    {
        init();
        startEchoLisitner();
        try
        {
            Packet.Builder payloadBuild = Builder.getBuilder(new PayloadBuilder(echoData), new UnknownPacket.Builder());
            Packet.Builder icmpBuilder = Builder.getBuilder(new IcmpEchoHeader(), payloadBuild);
            Packet.Builder icmpCommonBuilder = Builder.getBuilder(new IcmpV4CommonHeader(), icmpBuilder);
            Packet.Builder networkBuild = Builder.getBuilder
                    (
                            new IPV4Header(Builder.buildIpV4Address(strSrcIpAddress),Builder.buildIpV4Address(strDstIpAddress)),
                            icmpCommonBuilder
                    );
            Packet.Builder etherBuild = Builder.getBuilder
                    (
                            new EthernetHeader(Builder.buildMacAddress(strSrcMacAddress), Builder.buildMacAddress(strDstMacAddress)),
                            networkBuild
                    );
            for (int i = 0; i < COUNT; i++)
            {
                ((IcmpV4EchoPacket.Builder)icmpBuilder).sequenceNumber((short) i);
                ((IpV4Packet.Builder)networkBuild).identification((short) i);
                ((EthernetPacket.Builder)etherBuild).payloadBuilder(icmpBuilder);
                Packet p = etherBuild.build();
                sendHandle.sendPacket(p);
                try { Thread.sleep(100); }
                catch (InterruptedException e) { break; }
                try { Thread.sleep(400); }
                catch (InterruptedException e) { break; }
            }
        }
        catch(Exception ignored){}
        finally
        {
            if (handle != null && handle.isOpen())
            {
                try { handle.breakLoop(); }
                catch (NotOpenException noe) {}
                try { Thread.sleep(1000); }
                catch (InterruptedException e) {}
                handle.close();
            }
            if (sendHandle != null && sendHandle.isOpen()) { sendHandle.close(); }
            if (pool != null && !pool.isShutdown()) { pool.shutdown(); }
        }
    }

    private static class Task implements Runnable
    {

        private final PcapHandle handle;
        private final PacketListener listener;

        public Task(PcapHandle handle, PacketListener listener)
        {
            this.handle = handle;
            this.listener = listener;
        }
        @Override
        public void run()
        {
            try { handle.loop(-1, listener); }
            catch (PcapNativeException | NotOpenException e) { e.printStackTrace(); }
            catch (InterruptedException ignored) {}
        }
    }

}
