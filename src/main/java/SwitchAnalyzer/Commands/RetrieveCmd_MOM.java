package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Collectors.MOMConsumer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.MainHandler_MOM;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.ProduceData_MOM;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import java.util.ArrayList;


public class RetrieveCmd_MOM implements ICommandMOM
{
    public int id;
    public static Thread listeningThread;
    public ArrayList<String> retrievals;
    public ArrayList<Integer> ids;

    @Override
    public void processCmd()
    {
        GlobalVariable.retrieveDataFromNode = true;
        for (int i : ids)
        {
            GenCmd(new SwitchPort(i));
        }
        addCollectors();
        listeningThread = new Thread (() ->
        {
            while(GlobalVariable.retrieveDataFromNode)
            {
                ProduceData_MOM.produceData();
            }
        });
        listeningThread.start();
    }

    private void addCollectors()
    {
        for (String key : retrievals)
        {
            MOMConsumer.addCollector(MainHandler_MOM.collectors.get(key));
        }
    }

    @Override
    public void GenCmd(SwitchPort port)
    {
        RetrieveCmd_Master command = new RetrieveCmd_Master(port.ID);
        String json = JSONConverter.toJSON(command);
        json = "1" + json;
        GenericProducer producer = new GenericProducer(IP.ip1+":"+ Ports.port1);
        producer.send(Topics.cmdFromMOM, json);
        producer.close();
    }
}
