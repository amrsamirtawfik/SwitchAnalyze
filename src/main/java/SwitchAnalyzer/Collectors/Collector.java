package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.Topics;

public interface Collector {
    public String collect(String topic);
}
