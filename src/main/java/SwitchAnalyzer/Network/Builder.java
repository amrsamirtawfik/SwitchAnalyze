package SwitchAnalyzer.Network;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.UdpPort;
import org.pcap4j.util.MacAddress;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Builder
{
    public static Packet.Builder getBuilder(Header header , Packet.Builder prevBuilder)
    {
        return header.buildHeader(prevBuilder);
    }
    public static Inet4Address buildIpV4Address(String ip) throws UnknownHostException {
        return (Inet4Address) Inet4Address.getByName(ip);
    }
    public static MacAddress buildMacAddress(String mac) {
        return MacAddress.getByName(mac, ":");
    }

    public static void main (String [] args)
    {
        PayloadBuilder payloadBuilder = new PayloadBuilder("Testing");
        UDPHeader udpHeader = new UDPHeader((short) 12345, (short) 54321);
        IPV4Header ipv4Header = null;
        try
        {
            ipv4Header = new IPV4Header(buildIpV4Address("192.168.1.100"), buildIpV4Address("192.168.1.101"));
        }
        catch (Exception e)
        {
            System.out.println("couldn't make Ipv4 header");
        }
        EthernetHeader ethernetHeader = new EthernetHeader(buildMacAddress("00:00:00:00:00:01"), buildMacAddress("00:00:00:00:00:01"));

        PacketInfo packetInf = new PacketInfo(payloadBuilder, udpHeader, ipv4Header, ethernetHeader);

        Packet.Builder payloadBuild = getBuilder(payloadBuilder, new UnknownPacket.Builder());
        Packet.Builder udpBuild = getBuilder(udpHeader, payloadBuild);
        Packet.Builder networkBuild = getBuilder(ipv4Header, udpBuild);
        Packet.Builder etherBuild = getBuilder(ethernetHeader, networkBuild);
        Packet packet = etherBuild.build();
        System.out.println(packet);
        Packet.Builder builder = packet.getBuilder();
        UDPModifier.modifiyDstPort(builder, (short) 11111);
        PayloadModifier.modifiyPayload(builder, "Testing1");
        packet = builder.build();
        System.out.println(packet);
    }
}
