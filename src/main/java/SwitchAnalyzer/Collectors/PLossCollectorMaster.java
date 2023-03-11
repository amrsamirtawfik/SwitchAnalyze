package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Machines.MasterOfHPC;
import SwitchAnalyzer.NamingConventions;

/**
 * this class is responsible for collecting the rates from the machines
 * do some processing and then send return the overall rates so that
 * the master can send it to the MOM
 * the collector must have a name so that the master can identify it
 */
public class PLossCollectorMaster implements CollectorMaster {
    private String name = "PacketLoss";
    public String getName() {
        return name;
    }
    @Override
    public String collect(String topic)
    {

        float packetLoss = 0;
        String packetLossString;

        for (int i = 0; i < MasterOfHPC.childNodes.size(); i++) {
            //convert the string to a float
            packetLossString = MasterOfHPC.childNodes.get(i).machineInfo.map.get(NamingConventions.packetLoss);
            packetLoss += Float.parseFloat(packetLossString);
        }
        return String.valueOf(packetLoss);

    }

}
