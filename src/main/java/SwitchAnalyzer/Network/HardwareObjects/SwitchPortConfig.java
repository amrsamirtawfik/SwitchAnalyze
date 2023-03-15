package SwitchAnalyzer.Network.HardwareObjects;


import SwitchAnalyzer.Sockets.PacketInfoGui;
import SwitchAnalyzer.Network.Utilities;

import java.util.ArrayList;

public class SwitchPortConfig {
   public ArrayList<PacketInfoGui> packetInfos;
   public ArrayList<Utilities> utilities;
   public int rate;

   public String mode; // sender or receiver

    public SwitchPortConfig(ArrayList<PacketInfoGui> packetInfos, ArrayList<Utilities> utilities, int rate, String mode) {
        this.packetInfos = packetInfos;
        this.utilities = utilities;
        this.rate = rate;
        this.mode = mode;
    }
}


