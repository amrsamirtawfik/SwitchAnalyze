package SwitchAnalyzer;

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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainHandler_MOM {
    public static WebSocketServer server;
    public static Queue<ICommand> commands = new LinkedList<>();
    public static ArrayList<Class<? extends ICommandMOM>> commandClasses = new ArrayList<>();
    public static ArrayList<Collector> collectors = new ArrayList<>();
    static volatile int x;
    public static volatile MOM masterOfMasters;
    //TODO: should have an object of MOM in order to be used by the collectors?
    public static void init()
    {
        /*
            read the config text file and initialize the Global variables.
         */
        /*
        TODO: should initialize MOM and add all HPCs to it?
         */
        /*
            run the Mapping algorithm between ports and HPCs
         */
        server = new WebSocketServer(Ports.webSocketPort);
        MasterOfHPC master = new MasterOfHPC(0);
        GlobalVariable.portHpcMap.put(1, master);
//        GlobalVariable.portHpcMap.get(1).childNodes.add(new MachineNode(0));
        master.childNodes.add(new MachineNode(0));
        masterOfMasters = new MOM();
        masterOfMasters.HPCs.add(master);
        System.out.println(masterOfMasters.HPCs.get(0).getHPCID());
        System.out.println(masterOfMasters.HPCs.get(0));
        System.out.println(GlobalVariable.portHpcMap.get(1));
        commandClasses.add(StartRunCommand_MOM.class);
        commandClasses.add(RetrieveCmd_MOM.class);
        collectors.add(new RatesCollectorMOM());
        collectors.add(new PLossCollectorMOM());
    }

    public static void main(String[] args)
    {
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
