package SwitchAnalyzer;

import SwitchAnalyzer.Collectors.MasterConsumer;
import SwitchAnalyzer.Collectors.PLossCollectorMaster;
import SwitchAnalyzer.Collectors.RatesCollectorMaster;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import java.util.HashMap;
import java.util.Map;

import static SwitchAnalyzer.MainHandler_Master.master;

/**
 * this class is responsible for sending the metrics of HPC to kafka
 * the MOM is going to collect them and send them to the frontend through webSocket
 */
public class ProduceData_Master
{
    public static void produceData()
    {
        //TODO : this logic code be removed from here and put in the masterHandler and pass the result to produceData
        Map<String, String> results = MasterConsumer.consume();
        master.hpcInfo.map = (HashMap<String, String>) results;
        GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
        JSONConverter jsonConverter = new JSONConverter();
        String json = JSONConverter.toJSON(master.hpcInfo);
        System.out.println("ProduceData_Master: "+json);
        producer.send(Topics.ratesFromHPCs, json);
    }
}