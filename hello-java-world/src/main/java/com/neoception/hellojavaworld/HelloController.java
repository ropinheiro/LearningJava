package com.neoception.hellojavaworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String index() {
        return String.format("Hello Java and Kafka Worlds!%n")
                + String.format("Sending data to Kafka...%n")
                + KafkaUtils.SendMessages(env)
                + String.format("==============================================%n")
                + String.format("Receiving data from Kafka...%n")
                + KafkaUtils.ReadMessages(env);
    }
}
