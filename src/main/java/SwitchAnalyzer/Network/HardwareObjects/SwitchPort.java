package SwitchAnalyzer.Network.HardwareObjects;

public class SwitchPort {
    public int ID;
    public SwitchPortConfig portConfig;

    public SwitchPort(int ID)
    {
        this.ID = ID;
    }

    public SwitchPort(SwitchPortConfig portConfig, int ID) {
        this.portConfig = portConfig;
        this.ID = ID;
    }
}
