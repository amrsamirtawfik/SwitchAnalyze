package SwitchAnalyzer;

import SwitchAnalyzer.Collectors.*;
import SwitchAnalyzer.Commands.*;
import SwitchAnalyzer.Commands.ICommand;
import SwitchAnalyzer.Machines.MOM;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.Sockets.WebSocketServer;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.Machines.MasterOfHPC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class MainHandler_MOM {
    public static WebSocketServer server;
    public static Queue<ICommand> commands = new LinkedList<>();
    public static ArrayList<Class<? extends ICommandMOM>> commandClasses = new ArrayList<>();
    public static ArrayList<Collector> collectors = new ArrayList<>();
    static volatile int x;
    public static volatile MOM masterOfMasters;
    private static final Logger logger = Logger.getLogger(MainHandler_MOM.class.getName());
    //TODO: should have an object of MOM in order to be used by the collectors?
    public static void init()
    {
        logger.info("MainHandler_MOM: init");
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
        logger.info("MainHandler_MOM: init finished");
    }

    public static void main(String[] args)
    {
        logger.setLevel(GlobalVariable.level);
        logger.info("MainHandler_MOM: main");
        init();
        Thread t1 = new Thread(() -> UserRequestHandler.readCommands(server, Ports.webSocketPort,
                10000, commands));
        t1.start();
        logger.info("MainHandler_MOM: entering while loop");
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
