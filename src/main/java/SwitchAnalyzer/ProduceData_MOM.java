package SwitchAnalyzer;

import SwitchAnalyzer.Collectors.MOMConsumer;
import SwitchAnalyzer.Collectors.MasterConsumer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.Sockets.WebSocketServer;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import java.util.HashMap;
import java.util.Map;

import static SwitchAnalyzer.MainHandler_MOM.masterOfMasters;

public class ProduceData_MOM
{
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProduceData_MOM.class.getName());

    public static void produceData()
    {
        logger.setLevel(GlobalVariable.level);
        Map<String, String> results = MOMConsumer.consume();
        masterOfMasters.HPCs.get(0).hpcInfo.map =  results;
        JSONConverter jsonConverter = new JSONConverter();
        String json = JSONConverter.toJSON(masterOfMasters.HPCs.get(0).hpcInfo);
        logger.info("ProduceData_MOM: " + json);
        UserRequestHandler.writeToUser(MainHandler_MOM.server,json.getBytes());
    }
}