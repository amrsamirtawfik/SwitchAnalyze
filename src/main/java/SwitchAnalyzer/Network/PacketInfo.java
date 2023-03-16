package SwitchAnalyzer.Network;
public class PacketInfo
{
    DataLinkHeader dataLinkHeader;
    NetworkHeader networkHeader;
    TransportHeader transportHeader;
    PayloadBuilder payloadBuilder;

    ErrorDetectingAlgorithms errorDetectingAlgorithm;
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


}
