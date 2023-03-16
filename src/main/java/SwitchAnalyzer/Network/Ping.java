package SwitchAnalyzer.Network;

import java.net.UnknownHostException;

public abstract class Ping
{
   public Ping()
   {

   }
   public abstract void ping() throws UnknownHostException;
}
