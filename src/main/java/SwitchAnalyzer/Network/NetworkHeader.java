package SwitchAnalyzer.Network;

import java.net.Inet4Address;

public abstract class NetworkHeader extends Header
{
    public Inet4Address srcIPADDR ;
    public Inet4Address dstIPPADDR ;

    public NetworkHeader(Inet4Address srcIPADDR, Inet4Address dstIPPADDR)
    {
        this.srcIPADDR = srcIPADDR;
        this.dstIPPADDR = dstIPPADDR;
    }

    protected NetworkHeader() {}
}
