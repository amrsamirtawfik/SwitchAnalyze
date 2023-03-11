package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.Topics;

/**
 * this interface is for the masterOfHPC only
 */
public interface Collector {
    String collect();
    String getName();
}
