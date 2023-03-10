package SwitchAnalyzer.Network;

import org.pcap4j.packet.IcmpV4EchoPacket;

import java.net.UnknownHostException;

public abstract class Ping
{
   public Ping()
   {

   }
   public abstract void ping() throws UnknownHostException;
}
