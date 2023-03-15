/*
package SwitchAnalyzer;
*/
/**
 *This class represents a single machine in a network of machines that can receive commands from a master node through
 *Kafka messaging. The machine can operate in sender mode, where it sends packets to a specified destination and at a specified rate.
 *The class uses a Kafka consumer to receive commands from a topic and processes the commands based on the machine ID.
 *//*






import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.Kafka.*;
import SwitchAnalyzer.Commands.MachineCommand;
import SwitchAnalyzer.Network.*;
import SwitchAnalyzer.miscellaneous.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;

import java.net.UnknownHostException;

*/
/**
 * The main functionality of the SingleMachine class. It starts a thread to consume Kafka messages from a topic and
 * process them using the ContinuousConsume method.
 *//*

public class SingleMachine {
    static int   machineId = 0;
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> ContinuousConsume());
        t1.start();




    }
    */
/**
     * This method uses a Kafka consumer to continuously consume messages from a specified topic and processes them
     * based on the machine ID. It deserializes the JSON message to a MachineCommand object, and starts a new thread to
     * send packets if the command mode is "Sender" and the machine ID matches the current machine.
     *//*

    static void ContinuousConsume()
    {
        GenericConsumer consumer = new GenericConsumer(IP.singleMachine1Ip + ":" + Ports.singleMachine1Ip,"0");
        consumer.selectTopic(Topics.cmdFromHpcMaster);
        while(true)
        {
            ConsumerRecords<String, String> records =consumer.consume(Time.waitTime);

            for (ConsumerRecord<String, String> record : records) {
                String json = record.value();
                MachineCommand command = JSONConverter.fromJSON(json, MachineCommand.class);
                if(command.getMachineID() == machineId)
                {
                    Thread t1 = new Thread(() -> {
                        try {
                            handleCommand(command);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    t1.start();
                }
            }

        }
    }
    */
/**
     * This method handles a MachineCommand object by setting the sending rate and starting a new thread to send packets
     * to a specified destination.
     *//*

    static void  handleCommand(MachineCommand command) throws Exception {

        if (command.getMode().equals("Sender"))
        {
            // Set the sending rate
            System.out.println(command.getRates());
            // warning previously used was enp37s0
            RateController.setRate(GlobalVariable.interfaceName, Integer.toString((int)command.getRates()),Rates.machine1Rate );


            */
/*
                open a thread for sending with the command parameters
                should make the parameters more configurable later
             *//*

            Thread t1 = new Thread(() -> {
                try {
                    PacketGenerator.send(IP.receiverOfPackets, MacAddresses.Mac1, command.getPackets());
                } catch (PcapNativeException e) {
                    throw new RuntimeException(e);
                } catch (NotOpenException e) {
                    throw new RuntimeException(e);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            });
            t1.start();
        }
    }
}

*/
