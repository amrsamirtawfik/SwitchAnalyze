package SwitchAnalyzer;

import SwitchAnalyzer.Cluster.ClusterConfiguartions;
import SwitchAnalyzer.Cluster.MachineConfigurations;
import SwitchAnalyzer.Kafka.GenericConsumer;
import SwitchAnalyzer.Machines.HPC_INFO;
import SwitchAnalyzer.Machines.MachineNode;
import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class Main {

    static String consumerConfigurationGroup = "1";

    public static void main(String[] args) {
        MachineNode myNode=new MachineNode();

        GenericConsumer MainConsumer = new GenericConsumer(IP.ConfigurationsIP+ ":" + Ports.port1, consumerConfigurationGroup);


        while (true) {
            ConsumerRecords<String, String> records = MainConsumer.consume(100);
            for (ConsumerRecord<String, String> record : records) {
                ClusterConfiguartions ClusterConfig = JSONConverter.fromJSON(record.value(), ClusterConfiguartions.class);

                for (MachineConfigurations machineConfig:ClusterConfig.machines){
                    if (machineConfig.getMac().equals(myNode.getMyMacAddress())) {

                        //set machine node
                        myNode.setMachineID(machineConfig.getMachine_id());

                        Thread HandlerThread;
                        if (machineConfig.Is_master()) {
                            MasterOfHPC master=new MasterOfHPC(ClusterConfig.getCluster_Id(),ClusterConfig.machines.size(),100,myNode);
                            master.setArrayListOfMachines(ClusterConfig.machines);
                            //port number must be sent with the cluster configuration
                            GlobalVariable.portHpcMap.put(Ports.HPCportNumber,master);
                            HandlerThread= new Thread(() -> MainHandler_Master.main(null));
                            HandlerThread.start();
                        }else{
                            myNode.setRate(100);
                            MainHandler_Node.node=myNode;
                            HandlerThread= new Thread(() -> MainHandler_Node.main(null));
                            HandlerThread.start();
                        }

                    }
                }



            }
        }

    }
}