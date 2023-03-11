package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.NamingConventions;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import SwitchAnalyzer.miscellaneous.Time;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;

/**
 * this class is responsible for collecting the packetLoss from the machines
 * do some processing and then send return the overall packetLoss so that
 * the master can send it to the MOM
 * the collector must have a name so that the master can identify it
 * note I assumed that the the MasterOfHPC already have the machines in its list
 */
public class RatesCollector implements Collector{
    public static MasterOfHPC myHPC ;
    //the name of the collector is used to identify the collector in the results map
    private String name = "Rates";

    public String getName() {
        return name;
    }

    //    static String consumerGroup = "Collectors";
    @Override
    public String collect(String topic)
    {
            float OverallRate;
            // this variable is made because the result from is the map is a string
            String overAllRateString;
            OverallRate= 0;
            for (int i = 0; i < MasterOfHPC.childNodes.size(); i++) {
                //convert the string to a float
                overAllRateString = MasterOfHPC.childNodes.get(i).machineInfo.map.get(NamingConventions.rates);
                OverallRate += Float.parseFloat(overAllRateString);
            }
            return String.valueOf(OverallRate);


//            myHPC.setCurrentOverallRate(OverallRate);
//            String json = JSONConverter.toJSON(myHPC);
//            producer.send(Topics.ratesFromHPCs, json);


    }

}
