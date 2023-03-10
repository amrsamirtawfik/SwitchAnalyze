package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Network.HardwareObjects.SwitchPortConfig;
import SwitchAnalyzer.Network.PacketInfo;
import SwitchAnalyzer.Network.PacketSniffer;
import SwitchAnalyzer.Network.SendThreadsHandler;
import org.pcap4j.packet.Packet;

import static SwitchAnalyzer.MainHandler_Master.master;

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
        for (PacketInfo packetInfo : config.packetInfos)
            packetInfo.numberOfPackets = packetInfo.numberOfPackets / master.getNoOfChilNodes();
    }
    @Override
    public void processCmd()
    {
        //send or receive
        if (config.mode.equals("sender"))
        {
            for (PacketInfo packetInfo :config.packetInfos)
                SendThreadsHandler.addToPacketInfoList(packetInfo);
            SendThreadsHandler.openThreads();
        }
        else
        {
            long  numPackets = 0;
            long count = 0;
            Packet p;
            PacketSniffer sniffer = new PacketSniffer("port 777");
            for (PacketInfo packetInfo :config.packetInfos)
                numPackets+=packetInfo.numberOfPackets;
            while (count < numPackets)
            {
                p = sniffer.readPacket();
                //store p in kafka or database
            }
        }

    }
}
