package SwitchAnalyzer.Network;


import SwitchAnalyzer.miscellaneous.GlobalVariable;
import SwitchAnalyzer.Kafka.*;
import SwitchAnalyzer.Machines.*;
import SwitchAnalyzer.miscellaneous.*;

class Observer {
    // Returns the send and receive rates in kilobits per second
    static float[] getRate(float[] sendRate, float[] recRate) {
        String output;
        String buffSend = "";
        String buffRec = "";
        int outputIndx = 3;
        SystemAnalyser system = new SystemAnalyser();
        system.runCommand("sudo ./netscript.sh "+ GlobalVariable.interfaceName); // Run script in shell
        output = system.outputStore; // Output from terminal (command)
        System.out.println(output);
        System.out.println(output);
        System.out.println("hello \n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(output);
        System.out.println(output);
        // Get send rate
        while (output.charAt(outputIndx) != ':') {
            outputIndx++;
        }
        outputIndx += 2; // Was at ':' now first digit from rate
        while (output.charAt(outputIndx) != ' ') {
            buffSend += output.charAt(outputIndx);
            outputIndx++;
        }
        sendRate[0] = Float.parseFloat(buffSend);

        // Get rec rate
        while (output.charAt(outputIndx) != ':') {
            outputIndx++;
        }
        outputIndx += 2; // Was at ':' now first digit from rate
        while (output.charAt(outputIndx) != ' ') {
            buffRec += output.charAt(outputIndx);
            outputIndx++;
        }
        recRate[0] = Float.parseFloat(buffRec);

        return new float[] { sendRate[0], recRate[0] };
    }

    public static void observe()
    {
        GenericProducer producer = new GenericProducer(IP.ip1 + ":" + Ports.port1);
        MachineInfo machine=new MachineInfo(0,0);
        while (true)
        {
            float[] sendRate = new float[1];
            float[] recRate = new float[1];
            Observer.getRate(sendRate, recRate);

            machine.setRate((sendRate[0] * 8F)/1024);
            String json = JSONConverter.toJSON(machine);
            producer.send(Topics.ratesFromMachines,json);

            System.out.print((sendRate[0] * 8.0)/1024);
            System.out.println(" "+(recRate[0] * 8.0) / 1024);

        }
    }
}