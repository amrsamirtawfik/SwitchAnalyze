package SwitchAnalyzer;

import SwitchAnalyzer.Commands.ICommandMaster;
import SwitchAnalyzer.Network.Observer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Observer;
import SwitchAnalyzer.Network.PacketLoss.PacketLossCalculate;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.UtilityExecution.UtilityExecutor;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import com.google.gson.internal.bind.util.ISO8601Utils;

import static SwitchAnalyzer.MainHandler_Node.node;

/**
 * this class is responsible for sending the rates to kafka
 * so that the master could collect them and send them to the MOM
 */
public class ProduceData_Node {

    public static void produceData()
    {
        UtilityExecutor.executeUtils();
        node.machineInfo.map = UtilityExecutor.result;
        if(GlobalVariable.retrieveDataFromNode)
        {
            try
            {
                String json = JSONConverter.toJSON(node.machineInfo);
                MainHandler_Node.dataProducer.produce(json, Topics.ratesFromMachines);
                MainHandler_Node.dataProducer.flush();
            }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
