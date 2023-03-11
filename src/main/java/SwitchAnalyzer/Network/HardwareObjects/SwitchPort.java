package SwitchAnalyzer.Network.HardwareObjects;

public class SwitchPort {
    public SwitchPortConfig portConfig;
    public int ID;
    public SwitchPort(int ID)
    {
        this.ID = ID;
    }

    public SwitchPort(SwitchPortConfig portConfig, int ID) {
        this.portConfig = portConfig;
        this.ID = ID;
    }
}
