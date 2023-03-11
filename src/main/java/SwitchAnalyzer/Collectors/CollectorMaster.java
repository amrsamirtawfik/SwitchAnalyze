package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.Topics;

public interface CollectorMaster {
    String collect(String topic);
    String getName();
}
