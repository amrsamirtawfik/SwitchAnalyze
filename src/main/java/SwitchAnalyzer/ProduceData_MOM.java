package SwitchAnalyzer;

import SwitchAnalyzer.Collectors.MOMConsumer;
import SwitchAnalyzer.Sockets.JettyWebSocketServer;
import SwitchAnalyzer.Sockets.UserRequestHandler;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import java.util.ArrayList;

import static SwitchAnalyzer.MainHandler_MOM.masterOfMasters;

public class ProduceData_MOM
{
    public static void produceData(ArrayList<Integer> ids)
    {
        MOMConsumer.setRequestedData();
        String json;
        for (int id : ids)
        {
            json = JSONConverter.toJSON(masterOfMasters.HPCs.get(id).hpcInfo);
            JettyWebSocketServer.writeMessage(json);
        }
        if (!MOMConsumer.getResults().isEmpty())
        {
            json = JSONConverter.toJSON(MOMConsumer.getResults());
            JettyWebSocketServer.writeMessage(json);
        }
    }
}
