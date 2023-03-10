package SwitchAnalyzer.Sockets;

import SwitchAnalyzer.Commands.ICommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class UserCommunicationHandler {

    private static volatile int x = 0;
    static ArrayList<Class<? extends ICommand>> classes = new ArrayList<>();

    public static void listenToCommands(WebSocketServer server , int maxMessageLength, Queue<ICommand> cmdQueue)  {
        while (true) {
            byte[] command = server.readFromSocket(maxMessageLength);
            if (command == null){
                server.HandShake();
                continue;
            }
            try {
                parseCommand(command, cmdQueue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void parseCommand(byte[] command, Queue<ICommand> cmdQueue) throws JsonProcessingException {
        String jsonStr = new String(command);
        ObjectMapper mapper = new ObjectMapper();
        ICommand c = mapper.readValue(jsonStr,classes.get(0));
        cmdQueue.add(c);
    }

    public static void writeToUser (WebSocketServer server , byte[] writtenData) {
        server.writeToSocket(writtenData);
    }

//    public static void main(String[] args) {
//        Queue<Command> cmd_queue = new LinkedList<>();
//
//
//        Thread t1 = new Thread(() -> {
//              WebSocketServer server1 = new WebSocketServer(8080);
//              UserCommunicationHandler.listenToCommands(server1, 100000, cmd_queue);
//            });
//        t1.start();
//
//
//        Thread t2 = new Thread(() -> {
//                WebSocketServer server2 =  new WebSocketServer(9090);
//                while (true){
//                UserCommunicationHandler.writeToUser(server2,(new Scanner(System.in)).next().getBytes());
//            }
//          });
//        t2.start();
//
//        Thread t3 = new Thread(() -> {
//                 WebSocketServer server3 = new WebSocketServer(9099);
//                 UserCommunicationHandler.listenToCommands(server3, 100000, cmd_queue);
//        });
//        t3.start();
//
//        while (true) {
//
//            while (cmd_queue.peek() == null) {
//                x++;
//            }
//
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
//            System.out.println("************************OBJECT END********************************");
//            /*****************************************************************/
//        }
//    }
}
