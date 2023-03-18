package SwitchAnalyzer.Network.HardwareObjects;

public class SwitchPortPair
{
    public SwitchPort fromPort ;
    public int toPort;

    public SwitchPortPair(SwitchPort fromPort , int toPort)
    {
        this.fromPort = fromPort ;
        this.toPort = toPort;
    }
}
