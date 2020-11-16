package com.neoception.hellojavaworld;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    private final Environment env;

    public HelloController(Environment env) {
        this.env = env;
    }

    @RequestMapping("/")
    public String index() {
        return OutputUtils.Index();
    }

    @RequestMapping("/ping")
    public String ping() {
        return OutputUtils.PlainTextAnswer("PONG!");
    }

    // Send and receive messages to Kafka topics using Apache Kafka libs
    @RequestMapping("/kafkaWithApacheLibs")
    public String kafkaWithApacheLibs() {
        return String.format("Hello Java and Kafka Worlds!%n")
                + String.format("Sending data to Kafka...%n")
                + KafkaUtils.SendMessages(env)
                + String.format("==============================================%n")
                + String.format("Receiving data from Kafka...%n")
                + KafkaUtils.ReadMessages(env);
    }

    @PostMapping("/newMessage")
    public String newMessage(@RequestBody String message) {
        return String.format("Hello POST World!%n")
                + String.format("Sending data to Kafka...%n")
                + KafkaUtils.SendMessage(env, message)
                + String.format("==============================================%n")
                + String.format("Receiving data from Kafka...%n")
                + KafkaUtils.ReadMessages(env);
    }
}
