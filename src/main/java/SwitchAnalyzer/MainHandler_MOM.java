package SwitchAnalyzer;

import SwitchAnalyzer.Commands.ICommandMOM;
import SwitchAnalyzer.Commands.ProcessCmd;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.Machines.MasterOfHPC;

import java.util.LinkedList;
import java.util.Queue;

public class MainHandler_MOM {
    static Queue<ICommandMOM> commands = new LinkedList<>();
    static volatile int x;
    public static void init()
    {
        /*
            read the config text file and initialize the Global variables.
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
            ICommandMOM c = commands.poll();
            ProcessCmd.processCmd(c);
        }
    }
}
