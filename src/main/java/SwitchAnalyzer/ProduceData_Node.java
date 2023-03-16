package SwitchAnalyzer;

import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Observer;
import SwitchAnalyzer.Network.PacketLoss.PacketLossCalculate;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import static SwitchAnalyzer.MainHandler_Node.node;

/**
 * this class is responsible for sending the rates to kafka
 * so that the master could collect them and send them to the MOM
 */
public class ProduceData_Node {
    private static GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
    public static void produceData()
    {
            if(GlobalVariable.retrieveDataFromNode)
            {
                try
                {
                    node.machineInfo.map.put(NamingConventions.rates, Float.toString(Observer.getRate()));
                    // TODO : get the packet loss from the node
                    PacketLossCalculate packetLossCalculate = new PacketLossCalculate();
                    packetLossCalculate.startPacketLossTest();
                    node.machineInfo.map.put(NamingConventions.packetLoss,
                            String.valueOf((packetLossCalculate.COUNT - packetLossCalculate.recievedPacketCount)));
                    String json = JSONConverter.toJSON(node.machineInfo);
                    System.out.println("ProduceData_Node: "+json);
                    producer.send(Topics.ratesFromMachines, json);
                }
                catch (Exception e) { e.printStackTrace(); }
            }
    }
}
