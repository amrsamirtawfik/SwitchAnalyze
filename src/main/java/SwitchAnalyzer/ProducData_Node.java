package SwitchAnalyzer;

import SwitchAnalyzer.Network.Observer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import static SwitchAnalyzer.MainHandler_Node.node;

/**
 * this class is responsible for sending the rates to kafka
 * so that the master could collect them and send them to the MOM
 */
public class ProducData_Node {
    public void produceData()
    {
            if(GlobalVariable.retrieveDataFromNode)
            {
                GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
                node.machineInfo.map.put("Rates", Float.toString(Observer.getRate()));
                node.machineInfo.map.put("PacketLoss", "33");
                String json = JSONConverter.toJSON(node.machineInfo);
                producer.send(Topics.ratesFromMachines, json);
            }
    }
}
