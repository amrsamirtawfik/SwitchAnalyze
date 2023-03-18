package SwitchAnalyzer.Commands;

import SwitchAnalyzer.Collectors.MOMConsumer;
import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.MainHandler_MOM;
import SwitchAnalyzer.Network.HardwareObjects.SwitchPort;
import SwitchAnalyzer.ProduceData_MOM;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import SwitchAnalyzer.miscellaneous.SystemMaps;

import java.util.ArrayList;



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

    public void GenCmd(SwitchPort port)
    {
        String json = JSONConverter.toJSON(new RetrieveCmd_Master(port.ID, this.retrievals));
        json = "1" + json;
        MainHandler_MOM.cmdProducer.produce(json, Topics.cmdFromMOM);
        MainHandler_MOM.cmdProducer.flush();
    }

    private void addCollectors()
    {
        for (String key : retrievals)
        {
            MOMConsumer.addCollector(SystemMaps.collectors.get(key));
        }
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
}
