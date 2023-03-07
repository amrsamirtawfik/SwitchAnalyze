package SwitchAnalyzer.Network;

import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;

public class PayloadModifier {
    private static UnknownPacket.Builder payloadBuilder;

    public static void modifiyPayload(Packet.Builder builder, String payload)
    {
        payloadBuilder = builder.get(UnknownPacket.Builder.class);
        payloadBuilder.rawData(payload.getBytes());
    }
}
