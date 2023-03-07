package SwitchAnalyzer.miscellaneous;

import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import java.util.HashMap;
import java.util.Map;

public class GlobalVariable {
    public static Map<Integer, MasterOfHPC> portHpcMap = new HashMap<Integer,MasterOfHPC>();
    public static String interfaceName = "enp2s0";
    public static int webSocketPort = 9099;
    public static int webSocketMaxMessages = 100000;

}
