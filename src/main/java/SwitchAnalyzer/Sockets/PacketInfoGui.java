package SwitchAnalyzer.Sockets;

import SwitchAnalyzer.Network.*;
import SwitchAnalyzer.miscellaneous.GlobalVariable;

public class PacketInfoGui {
    public String dataLinkHeader;
    public String networkHeader;

    public String transportHeader;
    public String payloadBuilder;
    public String errorDetectingAlgorithm;
    public long packetSize;
    public long numberOfPackets;

    public PacketInfoGui(String dataLinkHeader, String networkHeader, String transportHeader, String payloadBuilder, String errorDetectingAlgorithm,long packetSize, long numberOfPackets) {
        this.dataLinkHeader = dataLinkHeader;
        this.networkHeader = networkHeader;
        this.transportHeader = transportHeader;
        this.payloadBuilder = payloadBuilder;
        this.errorDetectingAlgorithm=errorDetectingAlgorithm;
        this.packetSize = packetSize;
        this.numberOfPackets = numberOfPackets;
    }
}
