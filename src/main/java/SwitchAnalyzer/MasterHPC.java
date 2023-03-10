package SwitchAnalyzer;

import SwitchAnalyzer.Commands.*;
import SwitchAnalyzer.Commands.Command;
import SwitchAnalyzer.Kafka.*;
import SwitchAnalyzer.Machines.*;
import SwitchAnalyzer.miscellaneous.*;
import SwitchAnalyzer.Network.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MasterHPC {


    public static int noOfMachines = 2;
    private static int myHPCID = 0;
    public static MasterOfHPC myHPC ;
    //initialized with zeros
    public static ArrayList<MachineNode> sharedList = new ArrayList<>();
    public static String consumerGroup = "command-consumer-group";



    public static void main(String[] args) {
        initializeHPC();
        Logger logger = LoggerFactory.getLogger(MasterHPC.class.getName());
        GenericConsumer consumer=new GenericConsumer(IP.ip1 + ":" + Ports.port1,consumerGroup);
        consumer.selectTopic(Topics.cmdFromMOM);
        while(true){
            ConsumerRecords<String, String> records = consumer.consume(Time.waitTime);
            for (ConsumerRecord<String, String> record : records) {
                // Convert the JSON string to a Command object
                String json = record.value();
                Command command = JSONConverter.fromJSON(json, Command.class);
                if(command.getHPCID()==myHPC.getHPCID())
                {
                    logger.info("Key: " + record.key() + "value: " +record.value());
                    // Start a new thread to process the command
                    Thread t1 = new Thread(() -> processCommand(command));
                    t1.start();
                    // Start a new thread to compute the Overall HPC rate
                    Thread t2 = new Thread(() -> consumeAndSend());
                    t2.start();
                }
            }
        }
    }
    private static void initializeHPC(){
        //get this information from the configuration file of the HPC
        myHPC =new MasterOfHPC(myHPCID,noOfMachines,400);
        // initialize the arraylist
        for(int i = 0; i<myHPC.getNoOfChilNodes(); i++){
            sharedList.add(new MachineNode(i,0));
        }

    }

    /**
     * this function will take the command from MOM and send commands to  machines
     * @param command
     */
    private static void processCommand(Command command) {
        //rate of single machine = rates / number of machines
        float singleMachineRate = command.getRates()/noOfMachines;
        ArrayList<MachineCommand> MachineCmdList = new ArrayList<>(noOfMachines);
        // is it right to deduce from the health here?
        for(int i = 0; i < noOfMachines; i++)
        {
            MachineCmdList.add(new MachineCommand(i,singleMachineRate,"Sender",command.getPackets()/noOfMachines));
        }
        GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);

        for(MachineCommand cmd: MachineCmdList)
        {
            String json = JSONConverter.toJSON(cmd);

            producer.send(Topics.cmdFromHpcMaster,json);
        }
        // the thread terminates  automatically
    }

    /**
     * this function receives the rates from single machines and sum them up and  then send them to Master of masters
     */
    private static void consumeAndSend(){
        GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
        // Send the JSON string as a message to the "CMDFromMOM" Kafka topic

        float OverallRate;
        GenericConsumer consumer=new GenericConsumer(IP.ip1 + ":" + Ports.port1,consumerGroup);
        consumer.selectTopic(Topics.ratesFromMachines);

        while(true){
            int numberOfOffMachines =0;
            ConsumerRecords<String, String> records = consumer.consume(Time.waitTime);
            for (ConsumerRecord<String, String> record : records) {
                // Convert the JSON string to a Command object
                String json = record.value();
                System.out.println(json);
                System.out.println(json);
                System.out.println(json);
                System.out.println(json);
                System.out.println(json);

                MachineNode machineInfo = JSONConverter.fromJSON(json, MachineNode.class);
                System.out.println(machineInfo.getMachineID() + " " + machineInfo.getRate());
                System.out.println(machineInfo.getMachineID() + " " + machineInfo.getRate());
                System.out.println(machineInfo.getMachineID() + " " + machineInfo.getRate());
                System.out.println(machineInfo.getMachineID() + " " + machineInfo.getRate());
                MasterHPC.sharedList.set(machineInfo.getMachineID(), machineInfo);
            }
            OverallRate= 0;
            for (int i = 0; i < sharedList.size(); i++) {
                OverallRate += sharedList.get(i).getRate();
            }
            myHPC.setCurrentOverallRate(OverallRate);
            String json = JSONConverter.toJSON(myHPC);
            producer.send(Topics.ratesFromHPCs, json);
        }
    }
}