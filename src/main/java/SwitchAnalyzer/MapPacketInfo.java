package SwitchAnalyzer;

import SwitchAnalyzer.Network.*;
import SwitchAnalyzer.Sockets.PacketInfoGui;
import SwitchAnalyzer.miscellaneous.SystemMaps;


import java.util.HashMap;
import java.util.Map;

import static SwitchAnalyzer.miscellaneous.SystemMaps.packetInfoMap;

public class MapPacketInfo implements mapObjects
{
    @Override
    public  Object map(Object object)
    {
        Map<String , Header > map = new HashMap<>();
        SystemMaps.initPortInfoMap(map);
        PacketInfoGui packetGuiInfo = (PacketInfoGui) object;
        PacketInfo result = new PacketInfo
                (
                        new PayloadBuilder(packetGuiInfo.payloadBuilder),
                        (TransportHeader) map.get(packetGuiInfo.transportHeader),
                        (NetworkHeader) map.get(packetGuiInfo.networkHeader),
                        (DataLinkHeader) map.get(packetGuiInfo.dataLinkHeader)
                );
        result.numberOfPackets = packetGuiInfo.numberOfPackets;
        result.packetSize = packetGuiInfo.packetSize;
        result.payloadBuilder.payload = packetGuiInfo.payloadBuilder;
        return result;
    }
}
