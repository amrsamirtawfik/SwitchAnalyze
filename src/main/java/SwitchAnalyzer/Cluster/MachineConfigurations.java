package SwitchAnalyzer.Cluster;

import org.pcap4j.util.MacAddress;

public class MachineConfigurations {
    String machine_id;
    String ip;
    String mac;
    String is_master;

    public int getMachine_id() {
        return Integer.parseInt(machine_id);
    }

    public String getIp() {
        return ip;
    }

    public MacAddress getMac() {
        return toMacAdrress(mac);

    }
    public MacAddress toMacAdrress(String stringMacAddress){
        MacAddress retrievedMacAddress =MacAddress.getByName(stringMacAddress);
        return retrievedMacAddress;
    }

    public boolean Is_master() {
        return is_master.equalsIgnoreCase("True");
    }
}
