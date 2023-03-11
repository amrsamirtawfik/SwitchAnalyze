package SwitchAnalyzer.Collectors;

import SwitchAnalyzer.Kafka.Topics;

/**
 * this interface is for the masterOfHPC only
 */
public interface CollectorMaster {
    String collect();
    String getName();
}
