package SwitchAnalyzer;


import SwitchAnalyzer.Cluster.MachineConfigurations;
import SwitchAnalyzer.Cluster.MoMConfigurations;
import SwitchAnalyzer.Kafka.GenericConsumer;

import SwitchAnalyzer.Machines.MachineNode;

import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class Main {

    static String consumerConfigurationGroup = "A";

    public static void main(String[] args) {
        MachineNode myNode=new MachineNode();
            String ip="192.168.1.244";
        GenericConsumer MainConsumer = new GenericConsumer("localhost:9092", consumerConfigurationGroup);
MainConsumer.selectTopic("json");

        while (true) {
            ConsumerRecords<String, String> records = MainConsumer.consume(100);
            for (ConsumerRecord<String, String> record : records) {
                MoMConfigurations momConfigurations = JSONConverter.fromJSON(record.value(), MoMConfigurations.class);



                for (MachineConfigurations machineConfig:momConfigurations.cluster.get(0).machines){

                    if (machineConfig.getMac().equals(myNode.getMyMacAddress())) {

                        //set machine node
                        myNode.setMachineID(machineConfig.getMachine_id());

                        Thread HandlerThread;
                        if (machineConfig.Is_master()) {

                            MasterOfHPC master=new MasterOfHPC(momConfigurations.cluster.get(0).getCluster_Id(),momConfigurations.cluster.get(0).machines.size(),100,myNode);
                            master.setArrayListOfMachines(momConfigurations.cluster.get(0).machines);
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
