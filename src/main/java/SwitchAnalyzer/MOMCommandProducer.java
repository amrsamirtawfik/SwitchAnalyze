package SwitchAnalyzer;//package SwitchAnalyzer;
//
//import SwitchAnalyzer.Commands.Command;
//import SwitchAnalyzer.Kafka.*;
//import SwitchAnalyzer.Network.*;
//import SwitchAnalyzer.miscellaneous.*;
//
///**
//
// This class represents a producer that sends a message to a Kafka topic containing a serialized Command object.
// */
//public class MOMCommandProducer {
//
//    /**
//
//     The main method of the class. It creates a Command object, serializes it to JSON using a JSONConverter, and sends it as a message to a Kafka topic using a KafkaProducer.
//
//     @param args The command line arguments passed to the program (not used).
//     */
//    public static void main(String[] args) {
//// Create a Command object with some sample values
//        Command command = new Command(0, 1000, "Sender", 100);
//
//// Serialize the Command object to a JSON string using a JSONConverter
//        String json = JSONConverter.toJSON(command);
//
//// Create a KafkaProducer with the specified broker address
//        GenericProducer producer = new GenericProducer(IP.ip1+":"+ Ports.port1);
//
//// Send the JSON string as a message to the "CMDFromMOM" Kafka topic
//        producer.send(Topics.cmdFromMOM, json);
//
//// Close the KafkaProducer
//        producer.close();
//    }
//
//}
