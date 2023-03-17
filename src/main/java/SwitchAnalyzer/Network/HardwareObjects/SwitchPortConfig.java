package SwitchAnalyzer.Network.HardwareObjects;


import SwitchAnalyzer.Sockets.PacketInfoGui;

import java.util.ArrayList;

public class SwitchPortConfig {
   public ArrayList<PacketInfoGui> packetInfos;
    public ArrayList<String> utilities;
   public  long duration;
   public long sendingRate;

   public String mode; // sender or receiver

    public SwitchPortConfig(ArrayList<PacketInfoGui> packetInfos, ArrayList<String> utilities, int rate, String mode) {
        this.packetInfos = packetInfos;
        this.utilities = utilities;
        this.sendingRate = rate;
        this.mode = mode;
    }
}


