package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.Topics;

public interface Collector {
    String collect(String topic);
}
