package SwitchAnalyzer.Kafka;

import SwitchAnalyzer.Network.Ports;
import SwitchAnalyzer.miscellaneous.JSONConverter;

public class Producer
{
    GenericProducer producer;
    public Producer(String ip) { GenericProducer producer = new GenericProducer(ip + ":" + Ports.port1); }
    public void produce(String o, String topic)
    {
        producer.send(topic,o);
    }
    public void flush() { producer.flush(); }
    public void close() { producer.close(); }
}
