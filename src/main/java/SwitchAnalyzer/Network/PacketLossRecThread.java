package SwitchAnalyzer.Network;

public class PacketLossRecThread implements Runnable{
    PacketLossReciever packetLossReciever;
    @Override
    public void run()
    {
        packetLossReciever.echoPacket();
    }
}
