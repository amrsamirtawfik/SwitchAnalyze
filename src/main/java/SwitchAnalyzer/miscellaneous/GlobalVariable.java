package SwitchAnalyzer.miscellaneous;

import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.Network.Header;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class GlobalVariable {
    public static volatile boolean retrieveDataFromNode;
    public static Map<Integer, MasterOfHPC> portHpcMap = new HashMap<Integer,MasterOfHPC>();
    public static Map<String, Header> packetInfoMap = new HashMap<String, Header>();
    public static String interfaceName = "enp2s0";
    public static int webSocketPort = 9099;
    public static int webSocketMaxMessages = 100000;
    public static GenericProducer producer ;
    /**
     * thie variable is used to turn on/off the logging in the entire system
     * turn on : Level.AlL
     * turn off : Level.OFF
     */
    public static Level level = Level.ALL;
}
