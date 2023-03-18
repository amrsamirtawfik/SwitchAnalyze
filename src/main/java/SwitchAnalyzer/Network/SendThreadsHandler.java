package SwitchAnalyzer.Network;

import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Machines.MasterOfHPC;
import java.util.ArrayList;
import java.util.Vector;

public class SendThreadsHandler
{
    private static final ArrayList<PacketInfo> packetInfos = new ArrayList<>();

    public static void addToPacketInfoList(PacketInfo info) { packetInfos.add(info); }
    public static Vector<Thread> threads = new Vector<>();

    public static void sendToSelectedPort(int toPort, int rate, long duration)
    {
        ArrayList<Thread> threads = new ArrayList<>();
        MasterOfHPC selectedHPC = PortSelector.selectForPort(toPort);
        for (MachineNode node : selectedHPC.childNodes)
        {
            Thread t = new Thread(()-> openThreads(toPort , node, rate, duration));
            threads.add(t);
            t.start();
        }
        for (Thread thread : threads )
        {
            try { thread.join(); }
            catch (Exception ignored){}
        }
        clearPacketInfos();
        clearThreads();
    }

    public static void openThreads(int toPort , MachineNode node, int rate, long duration)
    {
        for (PacketInfo packetInfo : packetInfos)
        {
            packetInfo.setHeaderValues(node);
            NormalSender sender = new NormalSender(packetInfo.build() ,
                    packetInfo.numberOfPackets /PortSelector.selectForPort(toPort).childNodes.size(), rate, duration);
            threads.add (new Thread(sender));
            threads.get(threads.size()-1).start();
        }
    }

    public static void stopThreads()
    {
        try
        {
            for (Thread t : threads)
                t.wait();
        }
        catch (Exception ignored){}
    }

    public static void resumeThreads()
    {
        try
        {
            for (Thread t : threads)
                t.notify();
        }
        catch (Exception ignored){}
    }

    public static void clearThreads() { threads.clear(); }
    public static void clearPacketInfos() { packetInfos.clear(); }
}
