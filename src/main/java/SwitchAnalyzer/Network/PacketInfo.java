package SwitchAnalyzer.Network;
public class PacketInfo
{
    DataLinkHeader dataLinkHeader;
    NetworkHeader networkHeader;
    TransportHeader transportHeader;
    PayloadBuilder payloadBuilder;
    long packetSize;
    long numberOfPackets;

    public PacketInfo(PayloadBuilder payloadBuilder, TransportHeader transportHeader, NetworkHeader networkHeader,DataLinkHeader dataLinkHeader)
    {
        this.payloadBuilder = payloadBuilder;
        this.transportHeader = transportHeader;
        this.networkHeader = networkHeader;
        this.dataLinkHeader = dataLinkHeader;
    }
}
