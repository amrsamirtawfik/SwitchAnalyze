package SwitchAnalyzer;


import SwitchAnalyzer.Cluster.ClusterConfiguartions;
import SwitchAnalyzer.Cluster.MachineConfigurations;
import SwitchAnalyzer.Cluster.MoMConfigurations;
import SwitchAnalyzer.Kafka.GenericConsumer;

import SwitchAnalyzer.Kafka.Topics;
import SwitchAnalyzer.Machines.MOM;
import SwitchAnalyzer.Machines.MachineNode;

import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.Network.IP;
import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;

public class Main {

    static String consumerConfigurationGroup = "A";

    public static void main(String[] args) {
        MachineNode myNode =new MachineNode();
        GenericConsumer MainConsumer = new GenericConsumer(IP.ConfigurationsIP + ":" + Ports.port1, consumerConfigurationGroup);
        MainConsumer.selectTopic(Topics.configurations);

        while (true) {
            ConsumerRecords<String, String> records = MainConsumer.consume(100);
            for (ConsumerRecord<String, String> record : records) {
                MoMConfigurations momConfigurations = JSONConverter.fromJSON(record.value(), MoMConfigurations.class);

                Thread HandlerThread;
                if(momConfigurations.getMaster_Mac().equals(myNode.nodeMacAddress)){

                myNode.setNodeIp(momConfigurations.getMaster_Ip());
                myNode.setMachineID(-1);
                MOM masterOfMasters =new MOM(myNode);
                masterOfMasters.setHPCsInformation(momConfigurations.cluster);
                MainHandler_MOM.masterOfMasters= masterOfMasters;

                HandlerThread= new Thread(MainHandler_MOM::start);
                HandlerThread.start();

                }else{

                ClusterConfiguartions clusterConfigs=momConfigurations.cluster.get(0);
                ArrayList<MachineConfigurations> machineConfigList=clusterConfigs.machines;

                for (MachineConfigurations machineConfig:machineConfigList) {

                    if (machineConfig.getMac().equals(myNode.nodeMacAddress)) {
                        //set machine node
                        myNode.setMachineID(machineConfig.getMachine_id());
                        myNode.setNodeIp(machineConfig.getIp());

                        if (machineConfig.Is_master()) {

                            MasterOfHPC master = new MasterOfHPC(clusterConfigs.getCluster_Id(), clusterConfigs.getCluster_name(), myNode);
                            master.setChildNodes(machineConfigList);
                            MainHandler_Master.master = master;

                            HandlerThread= new Thread(MainHandler_Master::start);
                            HandlerThread.start();
                        } else {
                            MainHandler_Node.node = myNode;

                            HandlerThread= new Thread(MainHandler_Node::start);
                            HandlerThread.start();
                        }
                    }
                }
            }
                }




        }
        }

    }
