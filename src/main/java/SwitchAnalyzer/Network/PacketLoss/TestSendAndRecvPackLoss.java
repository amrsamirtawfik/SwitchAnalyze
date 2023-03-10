package SwitchAnalyzer.Network.PacketLoss;

import SwitchAnalyzer.Network.PacketSniffer;

public class TestSendAndRecvPackLoss
{
    public static void main(String []args)
    {
        PacketSniffer sniffer = new PacketSniffer("port 777");
        PacketLossSend p1 = new PacketLossSend();
        PacketLossReceive p2 = new PacketLossReceive(sniffer);
        // Used to set custom name to the current thread
        Runnable myThread1 = () ->
        {
            try
            {
                p1.ping();
            }
            catch (Exception e){}
        };
        Runnable myThread2 = () ->
        {
            while(true)
            {
                p2.receivePacket();

            }
            // Used to set custom name to the current thread
        };
        Thread sendThread = new Thread(myThread1);
        Thread recvThread= new Thread(myThread2);
        sendThread.start();
        recvThread.start();



        try
        {
            sendThread.join();
        }
        catch (Exception ex)
        {
            System.out.println("Error in join send thread");
        }

        //reach here when send thread finishes send window size
        try
        {

        Thread.sleep(1000);

        }
        catch (Exception e)
        {
            System.out.println("Can not sleep");

        }
        recvThread.interrupt();
        System.out.println("Diff = "+ (PacketLossSend.windowSize - p2.receivedPackets));

    }


}
