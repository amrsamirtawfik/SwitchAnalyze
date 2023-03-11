package SwitchAnalyzer.Network.PacketLoss;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import SwitchAnalyzer.Network.*;
import org.pcap4j.core.*;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IcmpV4CommonPacket;
import org.pcap4j.packet.IcmpV4EchoPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.IpV4Rfc791Tos;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.packet.namednumber.IcmpV4Code;
import org.pcap4j.packet.namednumber.IcmpV4Type;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.IpVersion;
import org.pcap4j.util.MacAddress;

import static SwitchAnalyzer.Network.PCAP.nif;

class SendFragmentedEcho
{

    private static final String COUNT_KEY = SendFragmentedEcho.class.getName() + ".count";
    private static final int COUNT = Integer.getInteger(COUNT_KEY, 3);

    private static final String READ_TIMEOUT_KEY =
            SendFragmentedEcho.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY = SendFragmentedEcho.class.getName() + ".snaplen";
    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    private static final String TU_KEY = SendFragmentedEcho.class.getName() + ".tu";
    private static final int TU = Integer.getInteger(TU_KEY, 4000); // [bytes]

    private static final String MTU_KEY = SendFragmentedEcho.class.getName() + ".mtu";
    private static final int MTU = Integer.getInteger(MTU_KEY, 1403); // [bytes]

    public static String strSrcIpAddress = "192.168.1.100"; // for InetAddress.getByName()
    public static String strSrcMacAddress = "54:EE:75:DF:82:C4"; // e.g. 12:34:56:ab:cd:ef
    public static String strDstIpAddress = "192.168.1.7"; // for InetAddress.getByName()
    public static String strDstMacAddress = "2C:F0:5D:59:F9:7C"; // e.g. 12:34:56:ab:cd:ef
    public static MacAddress srcMacAddr;
    public static byte[] echoData;
    static ExecutorService pool = Executors.newSingleThreadExecutor();

    private SendFragmentedEcho() {}

    public static void startEchoLisitner(PcapHandle handle) throws PcapNativeException
    {
        try
        {
            handle.setFilter("icmp and ether dst " + Pcaps.toBpfString(srcMacAddr), BpfCompileMode.OPTIMIZE);
            PacketListener listener = new PacketListener()
            {
                @Override
                public void gotPacket(PcapPacket packet)
                {
                    System.out.println(packet);
                }
            };
            Task t = new Task(handle, listener);
            pool.execute(t);
        }
        catch (Exception ignored){}
    }

    public static void echo() throws PcapNativeException
    {
        PcapHandle handle = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        PcapHandle sendHandle = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        try
        {
            Packet.Builder payloadBuild = Builder.getBuilder(new PayloadBuilder(Arrays.toString(echoData)), new UnknownPacket.Builder());
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
                try { Thread.sleep(1000); }
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
    public static void main(String[] args) throws PcapNativeException
    {
        PCAP.initialize();
        PcapHandle handle = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        PcapHandle sendHandle = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        ExecutorService pool = Executors.newSingleThreadExecutor();
        MacAddress srcMacAddr = MacAddress.getByName(strSrcMacAddress, ":");

        try
        {
            handle.setFilter("icmp and ether dst " + Pcaps.toBpfString(srcMacAddr), BpfCompileMode.OPTIMIZE);
            PacketListener listener = new PacketListener()
            {
                @Override
                public void gotPacket(PcapPacket packet)
                {
                    System.out.println(packet);
                }
            };
            Task t = new Task(handle, listener);
            pool.execute(t);

            byte[] echoData = new byte[1000];
            for (int i = 0; i < echoData.length; i++)
            {
                echoData[i] = 'a';
            }

            IcmpV4EchoPacket.Builder echoBuilder = new IcmpV4EchoPacket.Builder();
            echoBuilder
                    .identifier((short) 1)
                    .payloadBuilder(new UnknownPacket.Builder().rawData(echoData));

            IcmpV4CommonPacket.Builder icmpV4CommonBuilder = new IcmpV4CommonPacket.Builder();
            icmpV4CommonBuilder
                    .type(IcmpV4Type.ECHO)
                    .code(IcmpV4Code.NO_CODE)
                    .payloadBuilder(echoBuilder)
                    .correctChecksumAtBuild(true);

            IpV4Packet.Builder ipV4Builder = new IpV4Packet.Builder();
            ipV4Builder
                    .version(IpVersion.IPV4)
                    .tos(IpV4Rfc791Tos.newInstance((byte) 0))
                    .ttl((byte) 100)
                    .protocol(IpNumber.ICMPV4)
                    .srcAddr((Inet4Address) InetAddress.getByName(strSrcIpAddress))
                    .dstAddr((Inet4Address) InetAddress.getByName(strDstIpAddress))
                    .payloadBuilder(icmpV4CommonBuilder)
                    .correctChecksumAtBuild(true)
                    .correctLengthAtBuild(true);
            EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
            etherBuilder
                    .dstAddr(MacAddress.getByName(strDstMacAddress, ":"))
                    .srcAddr(srcMacAddr)
                    .type(EtherType.IPV4)
                    .paddingAtBuild(true);


            for (int i = 0; i < COUNT; i++)
            {
                echoBuilder.sequenceNumber((short) i);
                ipV4Builder.identification((short) i);
                etherBuilder.payloadBuilder(ipV4Builder);
                Packet p = etherBuilder.build();
                sendHandle.sendPacket(p);
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    break;
                }
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (handle != null && handle.isOpen())
            {
                try
                {
                    handle.breakLoop();
                }
                catch (NotOpenException noe) {}
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {}
                handle.close();
            }
            if (sendHandle != null && sendHandle.isOpen())
            {
                sendHandle.close();
            }
            if (pool != null && !pool.isShutdown())
            {
                pool.shutdown();
            }
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
            try
            {
                handle.loop(-1, listener);
            }
            catch (PcapNativeException | NotOpenException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException ignored) {}
        }
    }
}
