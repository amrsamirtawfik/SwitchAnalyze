package SwitchAnalyzer.Network.PacketLoss;

import java.net.UnknownHostException;

public class PacketLossSendThread implements Runnable
{
    PacketLossSend p1  = new PacketLossSend();

    @Override
    public void run()
    {
        try {
            p1.ping();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
