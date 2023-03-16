package SwitchAnalyzer.Network.ErrorDetection;

import org.pcap4j.packet.Packet;

public class None extends ErrorDetectingAlgorithms
{

    @Override
    public boolean isAlgorithmCorrect(byte[] packet)
    {
        return true;
    }

    @Override
    public Packet.Builder buildHeader(Packet.Builder prevBuilder)
    {
        return prevBuilder;
    }
}
