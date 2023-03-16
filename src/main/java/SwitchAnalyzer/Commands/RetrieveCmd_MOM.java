package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Collectors.MOMConsumer;
import SwitchAnalyzer.Kafka.GenericProducer;
import SwitchAnalyzer.Kafka.Producer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.MainHandler_MOM;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.ProduceData_MOM;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import java.util.ArrayList;

import static SwitchAnalyzer.MainHandler_MOM.cmdProducer;


public class RetrieveCmd_MOM implements ICommandMOM
{
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
        openConsumeAndProduceThread();
    }

    @Override
    public void GenCmd(SwitchPort port)
    {
        RetrieveCmd_Master command = new RetrieveCmd_Master(port.ID, this.retrievals);
        String json = JSONConverter.toJSON(command);
        json = "1" + json;
        cmdProducer.produce(json, Topics.cmdFromMOM);
        cmdProducer.flush();
    }

    private void openConsumeAndProduceThread()
    {
        Thread dataConsumeAndProduceThread = new Thread (() ->
        {
            while(GlobalVariable.retrieveDataFromNode)
            {
                ProduceData_MOM.produceData(ids);
            }
        });
        dataConsumeAndProduceThread.start();
    }

    private void addCollectors()
    {
        for (String key : retrievals)
        {
            MOMConsumer.addCollector(MainHandler_MOM.collectors.get(key));
        }
    }
}
