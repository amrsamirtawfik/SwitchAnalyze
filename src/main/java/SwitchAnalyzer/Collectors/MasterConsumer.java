package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineInfo;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import SwitchAnalyzer.miscellaneous.Time;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static SwitchAnalyzer.MainHandler_Master.master;

/**
 * this class will consume the overall info (for now rates+packet loss) information coming from the machines through kafka
 * then it will call all the  collectors available in the list to process the data
 * the collectors can be added using the addCollector method
 */

public class MasterConsumer {
    static String consumerGroup = "Collectors";
    static GenericConsumer consumer = new GenericConsumer(IP.ip1 + ":" + Ports.port1, consumerGroup);;

    //arraylist of collectors
    public static ArrayList<Collector> collectors = new ArrayList<>();
    //not needed because MasterOfHPC already has a list of machines
    //this map will contain the results of the collectors
    /*the key will be the collector name and the value will be the result
    * it is concurrent because it is accessed by multiple threads so it needs to be thread safe
     */
    static Map<String, String> results = new ConcurrentHashMap<>();
    public static Map<String, String> consume()
    {
        consumer.selectTopic(Topics.ratesFromMachines);
        /*
        TODO: there was an infinite loop here but i removed it because it should be made in the caller not here
         */
        while (true)
        {
            ConsumerRecords<String, String> records = consumer.consume(Time.waitTime);
            for (ConsumerRecord<String, String> record : records) {
                // Convert the JSON string to a Command object
                String json = record.value();

                MachineInfo machineInfo = JSONConverter.fromJSON(json, MachineInfo.class);
                // TODO: we need to add the machines first to the list of machines
                master.childNodes.get(machineInfo.machineID).machineInfo = machineInfo;
            }
            if(records.count() > 0)
                break;
        }
            //loop through the arraylist of collectors and create a thread for each one to call the collect method
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < collectors.size(); i++)
        {
            final int index = i; // Make a copy of i
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    String res = collectors.get(index).collect();
                    results.put(collectors.get(index).getName(), res);
                }
            });
            threads.add(thread);
            thread.start();
        }

// Wait for all threads to finish
        for (Thread thread : threads)
        {
            try
            {
                thread.join();
            } catch (InterruptedException e) {System.out.printf("in MasterConsumer: %s%n", e.getMessage());}
        }
        return results;
    }
    //add collectors to the arraylist
    public static void addCollector(Collector collectorMaster){ collectors.add(collectorMaster); }
    //remove collectors from the arraylist
    public static void removeCollector(Collector collectorMaster){ collectors.remove(collectorMaster); }

    public static Map<String, String> getResults() { return results; }
}
