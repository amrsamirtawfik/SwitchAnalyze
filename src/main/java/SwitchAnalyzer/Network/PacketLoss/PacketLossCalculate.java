package SwitchAnalyzer.Network.PacketLoss;

import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.MainHandler_Node;
import SwitchAnalyzer.Network.*;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static SwitchAnalyzer.Network.PCAP.nif;

public class PacketLossCalculate {

    public static final int COUNT = 10;
    private static final int READ_TIMEOUT = 10; //IN MS
    private static final int SNAPLEN = 65536;
    public  String echoData;
    public int recievedPacketCount = 0;
    private PcapHandle handle;
    private PcapHandle sendHandle;
    private final ExecutorService pool = Executors.newSingleThreadExecutor();


    private void init()
    {
        try
        {
            handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
            sendHandle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        }
        catch (Exception ignored){}
    }
    public void startEchoLisitner()
    {
        try
        {
            handle.setFilter("icmp and ether dst " + Pcaps.toBpfString(MainHandler_Node.node.nodeMacAddress), BpfProgram.BpfCompileMode.OPTIMIZE);
            PacketListener listener = packet -> recievedPacketCount++;
            Task t = new Task(handle, listener);
            pool.execute(t);
        }
        catch (Exception ignored){}
    }

    public void claculatePacketLoss()
    {
        init();
        startEchoLisitner();
        MasterOfHPC selectedHPC = PortSelector.selectRandomly();
        try
        {
            Packet.Builder payloadBuild = Builder.getBuilder(new PayloadBuilder(echoData), new UnknownPacket.Builder());
            Packet.Builder icmpBuilder = Builder.getBuilder(new IcmpEchoHeader(), payloadBuild);
            Packet.Builder icmpCommonBuilder = Builder.getBuilder(new IcmpV4CommonHeader(), icmpBuilder);
            Packet.Builder networkBuild = Builder.getBuilder
                    (
                        new IPV4Header(MainHandler_Node.node.nodeIp,selectedHPC.HPCIp),
                        icmpCommonBuilder
                    );
            Packet.Builder etherBuild = Builder.getBuilder
                    (
                        new EthernetHeader(MainHandler_Node.node.nodeMacAddress, selectedHPC.HPCMacAddr),
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
                catch (NotOpenException ignored) {}
                try { Thread.sleep(1000); }
                catch (InterruptedException ignored) {}
                handle.close();
            }
            if (sendHandle != null && sendHandle.isOpen()) { sendHandle.close(); }
            if (!pool.isShutdown()) { pool.shutdown(); }
        }
    }

    public static float startPacketLossTest()
    {
        PacketLossCalculate packetLossCalculate = new PacketLossCalculate();
        packetLossCalculate.claculatePacketLoss();
        return (((float)COUNT - packetLossCalculate.recievedPacketCount)/COUNT) * 100;
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
