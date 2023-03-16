package SwitchAnalyzer;//package SwitchAnalyzer;
//
//import SwitchAnalyzer.miscellaneous.GlobalVariable;
//import SwitchAnalyzer.Kafka.*;
//import SwitchAnalyzer.Machines.*;
//import SwitchAnalyzer.Network.*;
//import SwitchAnalyzer.Sockets.WebSocketServer;
//import SwitchAnalyzer.miscellaneous.*;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//
///**
// * this is the master of masters where it initiates a connection with the GUI using web sockets
// * it then takes messages (commands) from websockets and then initiate the mainHandler
// * the mainHandler creates a thread for producing the command and another thread for consuming the rates from singleMachines
// */
//
//// fares
//public class MOM {
//    public static ArrayList<MasterOfHPC> HPCsList = new ArrayList<MasterOfHPC>();
//    private static int noOfHPCs=1;
//    static String readMessage;
//    static byte[] readBytes;
//    static WebSocketServer websocket;
//    static String consumerGroup = "MoM-consumer-group";
//    public static void main(String[] args)
//    {
//        websocket = new WebSocketServer(GlobalVariable.webSocketPort);
//        while(true)
//        {
//            readBytes = websocket.readFromSocket(GlobalVariable.webSocketMaxMessages);
//            if(readBytes != null)
//            {
//                Thread t1 = new Thread(() -> mainHandler());
//                t1.start();
//            }
//            else{
//                websocket.HandShake();
//            }
//        }
//    }
//
//    static void mainHandler()
//    {
//        readMessage = new String(readBytes);
//        initializeHPCs();
//        Logger logger = LoggerFactory.getLogger(MOM.class.getName());
//        Thread t1 = new Thread(() -> commandsProducer());
//        t1.start();
//        Thread t2 = new Thread(() -> ratesConsumer());
//        t2.start();
//    }
//    private static void initializeHPCs()
//    {
//        for (int i=0;i<noOfHPCs;i++){
//            HPCsList.add(new MasterOfHPC(i));
//        }
//    }
//    private static void commandsProducer(){
//
//        // Create a KafkaProducer with the specified broker address
//        GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
//        // Send the JSON string as a message to the "CMDFromMOM" Kafka topic
//        producer.send(Topics.cmdFromMOM, readMessage);
//    }
//    private static void ratesConsumer(){
//        GenericConsumer consumer=new GenericConsumer(IP.ip1 + ":" + Ports.port1,consumerGroup);
//
//        // Subscribe to the "RatesFromHPCs" topic
//        consumer.selectTopic(Topics.ratesFromHPCs);
//        while(true){
//            ConsumerRecords<String, String> records = consumer.consume(Time.waitTime);
//            for (ConsumerRecord<String, String> record : records) {
//                // Convert the JSON string to a Command object
//                String json = record.value();
//                MasterOfHPC HPCprop = JSONConverter.fromJSON(json, MasterOfHPC.class);
//                HPCsList.set(HPCprop.getHPCID(),HPCprop);
//                for(int i = 0 ; i < noOfHPCs; i++) {
//                    String s = Float.toString(HPCsList.get(i).getCurrentOverallRate());
//                    websocket.writeToSocket(s.getBytes());
//                }
//            }
//
//        }
//    }
//}
