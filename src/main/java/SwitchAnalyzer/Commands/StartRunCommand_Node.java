package SwitchAnalyzer.Commands;

import SwitchAnalyzer.MainHandler_Master;
import SwitchAnalyzer.MapPacketInfo;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.Network.PacketInfo;
import SwitchAnalyzer.Network.PacketSniffer;
import SwitchAnalyzer.Network.SendThreadsHandler;
import SwitchAnalyzer.ProduceData_Node;
import SwitchAnalyzer.Sockets.PacketInfoGui;
import SwitchAnalyzer.UtilityExecution.UtilityExecutor;
import SwitchAnalyzer.miscellaneous.SystemMaps;
import org.pcap4j.packet.Packet;

public class StartRunCommand_Node extends ICommandNode
{
    SwitchPortConfig config;

    StartRunCommand_Node (SwitchPortConfig config, int ID)
    {
        machineID = ID;
        this.config = config ;
        distNoPackets();
    }

    public void distNoPackets()
    {
        config.rate = config.rate/ MainHandler_Master.master.getNoOfChilNodes();
        for (PacketInfoGui packetInfo : config.packetInfos)
        {
            packetInfo.numberOfPackets = packetInfo.numberOfPackets / MainHandler_Master.master.getNoOfChilNodes();
        }
    }

    @Override
    public void processCmd()
    {
        addUtils();
        openProduceThread();
        openSendAndRecThreads();
    }

    public void addUtils()
    {
        for (String key : config.utilities)
        {
            UtilityExecutor.executors.add(SystemMaps.executorHashMap.get(key));
        }
    }

    public void openProduceThread()
    {
        Thread executeUtilitiesThread = new Thread (() ->
        {
            while(!UtilityExecutor.executors.isEmpty())
            {
                ProduceData_Node.produceData();
            }
        });
        if(!UtilityExecutor.executors.isEmpty()) { executeUtilitiesThread.start(); }
    }

    public void openSendAndRecThreads()
    {
        if (config.mode.equals("sender"))
        {
            for (PacketInfoGui packetInfo :config.packetInfos)
            {
                SendThreadsHandler.addToPacketInfoList((PacketInfo) new MapPacketInfo().map(packetInfo));
            }
            SendThreadsHandler.openThreads();
        }
        else
        {
            long  numPackets = 0;
            long count = 0;
            Packet p;
            PacketSniffer sniffer = new PacketSniffer("port 777");
            for (PacketInfoGui packetInfo :config.packetInfos)
                numPackets+=packetInfo.numberOfPackets;
            while (count < numPackets)
            {
                p = sniffer.readPacket();
                System.out.println(p);
                count++;
            }
        }
    }

}
