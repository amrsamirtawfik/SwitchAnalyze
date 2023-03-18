package SwitchAnalyzer.UtilityExecution;

import SwitchAnalyzer.NamingConventions;
import SwitchAnalyzer.Network.PacketLoss.PacketLossCalculate;

public class PacketLossExecutor implements IExecutor
{
    public void execute()
    {
        UtilityExecutor.result.put(NamingConventions.packetLoss, Float.toString(PacketLossCalculate.startPacketLossTest()));
    }
}
