package SwitchAnalyzer.Network;

import SwitchAnalyzer.Network.ErrorDetection.ErrorDetectingAlgorithms;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;

public class PacketInfo
{
    DataLinkHeader dataLinkHeader;
    NetworkHeader networkHeader;
    TransportHeader transportHeader;
    PayloadBuilder payloadBuilder;

    ErrorDetectingAlgorithms errorDetectingAlgorithm;

    public boolean insertErrors;
    public long packetSize;
    public long numberOfPackets;



    public PacketInfo(PayloadBuilder payloadBuilder, TransportHeader transportHeader, NetworkHeader networkHeader,DataLinkHeader dataLinkHeader,ErrorDetectingAlgorithms errorDetectingAlgorithm)
    {
        this.payloadBuilder = payloadBuilder;
        this.transportHeader = transportHeader;
        this.networkHeader = networkHeader;
        this.dataLinkHeader = dataLinkHeader;
        this.errorDetectingAlgorithm=errorDetectingAlgorithm;
    }

    public Packet build()
    {
        Packet.Builder payloadBuild = Builder.getBuilder(this.payloadBuilder, new UnknownPacket.Builder());
        Packet.Builder udpBuild = Builder.getBuilder(this.transportHeader, payloadBuild);
        Packet.Builder networkBuild = Builder.getBuilder(this.networkHeader, udpBuild);
        Packet.Builder etherBuild = Builder.getBuilder(this.dataLinkHeader, networkBuild);
        Packet.Builder CRCBuild = Builder.getBuilder(this.errorDetectingAlgorithm,etherBuild);
        return CRCBuild.build();
    }
}
