package SwitchAnalyzer;

import SwitchAnalyzer.Commands.*;
import SwitchAnalyzer.Commands.ICommand;
import SwitchAnalyzer.Kafka.Producer;
import SwitchAnalyzer.Machines.MOM;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Collectors.Collector;
import SwitchAnalyzer.Collectors.PLossCollectorMOM;
import SwitchAnalyzer.Collectors.RatesCollectorMOM;
import SwitchAnalyzer.Commands.*;
import SwitchAnalyzer.Machines.MOM;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.Sockets.WebSocketServer;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.miscellaneous.SystemMaps;


import java.util.LinkedList;
import java.util.Queue;

public class MainHandler_MOM {
    public static WebSocketServer server;
    public static Producer cmdProducer = new Producer(IP.ip1);
    static volatile int x;
    public static volatile MOM masterOfMasters;

    public static void init()
    {
        SystemMaps.initMapsMOM();
        server = new WebSocketServer(Ports.webSocketPort);
        MasterOfHPC master = new MasterOfHPC(0);
        GlobalVariable.portHpcMap.put(1, master);
        master.childNodes.add(new MachineNode(0));
        masterOfMasters = new MOM();
        masterOfMasters.HPCs.add(master);
    }

    public static void main(String[] args)
    {
        Queue<ICommand> commands = new LinkedList<>();
        init();
        Thread t1 = new Thread(() -> UserRequestHandler.readCommands(server, Ports.webSocketPort,
                10000, commands));
        t1.start();
        while(true)
        {
            while (commands.peek() == null)
            {
                x++;
            }
            ICommand c = commands.poll();
            ProcessCmd.processCmd(c);
        }

    }
}
