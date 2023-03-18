package SwitchAnalyzer.miscellaneous;

import SwitchAnalyzer.Collectors.*;
import SwitchAnalyzer.Commands.*;
import SwitchAnalyzer.NamingConventions;
import SwitchAnalyzer.Network.*;
import SwitchAnalyzer.UtilityExecution.IExecutor;
import SwitchAnalyzer.UtilityExecution.RateExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SystemMaps
{
    public static ArrayList<Class<? extends ICommand>> commandClasses = new ArrayList<>();
    public static ArrayList<Class<? extends ICommandMaster>> commandClassesMaster = new ArrayList<>();
    public static HashMap<String, Collector> collectors = new HashMap<>();
    public static ArrayList<Class<? extends ICommandNode>> commandClassesNode = new ArrayList<>();
    public static HashMap<String, IExecutor> executorHashMap= new HashMap<>();
    public static Map<String, Header> packetInfoMap = new HashMap<>();

    public static void initMapsMOM()
    {
        collectors.put(NamingConventions.rates,new RatesCollectorMOM());
        collectors.put(NamingConventions.packetLoss,new PLossCollectorMOM());
        commandClasses.add(StartRunCommand_MOM.class);
        commandClasses.add(RetrieveCmd_MOM.class);
        commandClasses.add(StopRetrieveCmd_MOM.class);
    }

    public static void initMapsMaster()
    {
        commandClassesMaster.add(StartRunCommand_Master.class);
        commandClassesMaster.add(RetrieveCmd_Master.class);
        commandClassesMaster.add(StopRetrieveCmd_Master.class);
        collectors.put(NamingConventions.rates, new RatesCollectorMaster());
        collectors.put(NamingConventions.packetLoss, new PLossCollectorMaster());
    }

    public static void initMapsNode()
    {
        packetInfoMap.put("udp",new UDPHeader());
        packetInfoMap.put("tcp",new TCPHeader());
        packetInfoMap.put("ipv4",new IPV4Header());
        packetInfoMap.put("ipv6",new IPV6Header());
        executorHashMap.put(NamingConventions.rates, new RateExecutor());
        commandClassesNode.add(StartRunCommand_Node.class);
        commandClassesNode.add(RetrieveCmd_Node.class);
        commandClassesNode.add(StopRetrieveCmd_Node.class);
    }

    public static void initPortInfoMap(Map <String , Header> map)
    {
        map.put("udp",new UDPHeader());
        map.put("tcp",new TCPHeader());
        map.put("ipv4",new IPV4Header());
        map.put("ipv6",new IPV6Header());
        map.put("Ethernet" ,new EthernetHeader());
    }
}
