package SwitchAnalyzer;

import SwitchAnalyzer.Collectors.MOMConsumer;
import SwitchAnalyzer.Collectors.MasterConsumer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.Sockets.WebSocketServer;
import SwitchAnalyzer.miscellaneous.JSONConverter;

import java.util.HashMap;
import java.util.Map;

import static SwitchAnalyzer.MainHandler_MOM.masterOfMasters;
import static SwitchAnalyzer.MainHandler_Master.master;

public class ProduceData_MOM
{
    public static void produceData()
    {
        Map<String, String> results = MOMConsumer.consume();
        masterOfMasters.HPCs.get(0).hpcInfo.map = (HashMap<String, String>) results;
        JSONConverter jsonConverter = new JSONConverter();
        String json = JSONConverter.toJSON(master.hpcInfo);
        System.out.println("ProduceData_MOM: " + json);
        UserRequestHandler.writeToUser(MainHandler_MOM.server,json.getBytes());
    }
}