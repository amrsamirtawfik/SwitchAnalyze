//package SwitchAnalyzer.Collectors;
//
//import SwitchAnalyzer.Kafka.GenericConsumer;
//import SwitchAnalyzer.Kafka.GenericProducer;
//import SwitchAnalyzer.Kafka.Topics;
//import SwitchAnalyzer.Machines.MachineNode;
//import SwitchAnalyzer.Machines.MasterOfHPC;
//import SwitchAnalyzer.Network.IP;
//import SwitchAnalyzer.Network.Ports;
//import SwitchAnalyzer.miscellaneous.JSONConverter;
//import SwitchAnalyzer.miscellaneous.Time;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//
//import java.util.ArrayList;
//
//public class RatesCollector implements Collector{
//    public static ArrayList<MachineNode> sharedList = new ArrayList<>();
//    public static MasterOfHPC myHPC ;
//    static String consumerGroup = "Collectors";
//    @Override
//    public String collect(String topic)
//    {
//        GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
//        // Send the JSON string as a message to the "CMDFromMOM" Kafka topic
//
//        float OverallRate;
//        GenericConsumer consumer=new GenericConsumer(IP.ip1 + ":" + Ports.port1,consumerGroup);
//        consumer.selectTopic(Topics.ratesFromMachines);
//
//        while(true){
//            int numberOfOffMachines =0;
//            ConsumerRecords<String, String> records = consumer.consume(Time.waitTime);
//            for (ConsumerRecord<String, String> record : records) {
//                // Convert the JSON string to a Command object
//                String json = record.value();
//
//                MachineNode machineInfo = JSONConverter.fromJSON(json, MachineNode.class);
//                MasterHPC.sharedList.set(machineInfo.getMachineID(), machineInfo);
//            }
//            OverallRate= 0;
//            for (int i = 0; i < sharedList.size(); i++) {
//                OverallRate += sharedList.get(i).getRate();
//            }
//            return String.valueOf(OverallRate);
//
//
////            myHPC.setCurrentOverallRate(OverallRate);
////            String json = JSONConverter.toJSON(myHPC);
////            producer.send(Topics.ratesFromHPCs, json);
//
//        }
//    }
//}
