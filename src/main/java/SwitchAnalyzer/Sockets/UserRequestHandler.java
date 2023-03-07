package SwitchAnalyzer.Sockets;


import SwitchAnalyzer.Commands.Command;
import SwitchAnalyzer.Commands.ICommand;
import SwitchAnalyzer.Commands.StartRunCommand_MOM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public class UserRequestHandler {
    static volatile int x = 0;
    static ArrayList<Class<? extends ICommand>> classes = new ArrayList<>();
    public static void readCommands(int portNumber, int maxMessageLength, Queue<ICommand> cmdQueue)
    {
        WebSocketServer webSocketServer = new WebSocketServer(portNumber);
        while (true) {
            byte[] command = webSocketServer.readFromSocket(maxMessageLength);
            if (command == null){
                webSocketServer.HandShake();
                continue;
            }
            try {
                parseCommand(command, cmdQueue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }




    public static void parseCommand(byte[] command, Queue<ICommand> cmdQueue) throws JsonProcessingException
    {
        classes.add(StartRunCommand_MOM.class);
        String jsonStr = new String(command);
        ObjectMapper mapper = new ObjectMapper();
        ICommand c = mapper.readValue(jsonStr, classes.get(0));
        cmdQueue.add(c);
    }
    public static void main(String[] args) {
//        Queue<Command> cmd_queue = new LinkedList<>();
//
//        Thread t1 = new Thread(() -> {
//            readCommands(8080, 100000, cmd_queue);
//            });
//        t1.start();
//
//        while (true) {
//            while (cmd_queue.peek() == null) {
//                x++;
//            }
//            Command c = cmd_queue.poll();
//            System.out.println("************************OBJECT START********************************");
//            System.out.println("Packet Size: " + c.getPacketSize() +
//                    "\nNumber of Packets: " + c.getPacketsNum() +
//                    "\nRate: " + c.getRate());
//            for (int i = 0; i < c.getPacketConfig().size(); i++) {
//                System.out.println("Number: " + i);
//                System.out.println("Data Link Header: " + c.getPacketConfig().get(i).getDataLinkHeader());
//                System.out.println("Network Header: " + c.getPacketConfig().get(i).getNetworkHeader());
//                System.out.println("Transport Header: " + c.getPacketConfig().get(i).getTransportHeader());
//                System.out.println("PayLoad Builder: " + c.getPacketConfig().get(i).getPayloadBuilder());
//            }
//            System.out.println("\n\n");
//            for (int i = 0; i < c.getPortNums().size(); i++) {
//                System.out.println("Number: " + i);
//                System.out.println("Port: " + c.getPortNums().get(i));
//            }
//            System.out.println("\n\n");
//            for (int i = 0; i < c.getUtilities().size(); i++) {
//                System.out.println("Number: " + i);
//                System.out.println("Utility: " + c.getUtilities().get(i).getUtility());
//            }
            System.out.println("************************OBJECT END********************************");
            /*****************************************************************/
    }
}