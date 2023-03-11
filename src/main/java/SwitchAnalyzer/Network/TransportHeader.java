package SwitchAnalyzer.Network;
public abstract class TransportHeader extends Header
{
    public short srcPort;
    public short dstPort;
    public boolean addChecksum = false;

    public TransportHeader(short dstPort , short srcPort )
    {
        this.dstPort = dstPort;
        this.srcPort = srcPort;
    }
    public TransportHeader(){};
}
