package SwitchAnalyzer.Kafka;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GenericProducer {
    private final KafkaProducer<String, String> producer;

    /**
     *
     * @param topic
     * @param bootstrapServers should be in this syntax "192.168.1.4:9092"
     */
    public GenericProducer(String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);

    }

    /**
     *
     * @param topic
     * @param value
     */
    public void send( String topic,String value) {

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
        this.producer.send(record);
    }

    public void close() {
        producer.flush();
        this.producer.close();
    }

    public void flush()
    {
        producer.flush();
    }
}
