package SwitchAnalyzer;

import SwitchAnalyzer.Commands.ICommand;
import SwitchAnalyzer.Commands.ICommand;
import SwitchAnalyzer.Commands.ProcessCmd;
import SwitchAnalyzer.Machines.MOM;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.Machines.MasterOfHPC;

import java.util.LinkedList;
import java.util.Queue;

public class MainHandler_MOM {
    static Queue<ICommand> commands = new LinkedList<>();
    static volatile int x;
    public static MOM masterOfMasters;
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
        GlobalVariable.portHpcMap.put(1, new MasterOfHPC(1));
    }

    public static void main(String[] args)
    {
        init();
        Thread t1 = new Thread(() -> UserRequestHandler.readCommands(Ports.webSocketPort, 8888, commands));
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
