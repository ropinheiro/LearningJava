package com.neoception.hellojavaworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.groupId}")
    private String groupId;

    @Value("${kafka.messageCount}")
    private Integer messageCount;


    @RequestMapping("/")
    public String index() {
        return String.format("Hello Java and Kafka Worlds!%n")
                + String.format("Sending data to Kafka...%n")
                + KafkaUtils.SendMessages(topic, messageCount)
                + String.format("==============================================%n")
                + String.format("Receiving data from Kafka...%n")
                + KafkaUtils.ReadMessages(topic, groupId, messageCount);
    }
}
