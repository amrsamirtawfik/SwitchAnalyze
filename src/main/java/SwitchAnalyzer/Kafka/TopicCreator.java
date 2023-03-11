package com.example.kafka;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.*;

public class TopicCreator {
    public void createTopic (String brokersAddresses , String topicName , int numberOfPartitions , int repFactor){
        // Set up the Kafka AdminClient configuration
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokersAddresses);
        AdminClient adminClient = AdminClient.create(properties);

        // Set up the configuration for the topic to be created
        Map<String, String> topicConfig = new HashMap<String, String>();
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");

        // Set up the topic creation request
        NewTopic newTopic = new NewTopic(topicName, numberOfPartitions, (short)repFactor).configs(topicConfig);

        // Send the topic creation request to Kafka
        CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singleton(newTopic));

        // Wait for the topic creation to finish
        try {
            createTopicsResult.all().get();
            System.out.println("Topic " + topicName+" is created successfully!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Close the AdminClient
        adminClient.close();
    }

    public static void main(String[] args) {
    TopicCreator test = new TopicCreator();
    test.createTopic("localhost:9093 , localhost:9094 , localhost:9095", "JAAVAAAA",3,3);

    }
}
