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
    private final KafkaUtils utils;

    // Shows an index with the available options
    @RequestMapping("/")
    public String index() {
        return OutputUtils.Index();
    }

    // Just a ping to validate basic request/response is working
    @RequestMapping("/ping")
    public String ping() {
        return OutputUtils.PlainTextAnswer("PONG!");
    }

    // ====================================================================
    // Methods using Apache Kafka libs and Environment
    // ====================================================================

    // Send and receive messages to Kafka topics using Apache Kafka libs
    @RequestMapping("/kafkaWithApacheLibs")
    public String kafkaWithApacheLibs() {
        return OutputUtils.ReturnToIndex()
                + OutputUtils.Line("Sending data to Kafka...")
                + KafkaUtils.SendMessages(env)
                + OutputUtils.Line(OutputUtils.Bar)
                + OutputUtils.Line("Receiving data from Kafka...")
                + KafkaUtils.ReadMessages(env);
    }

    // To be used with Postman: post something.
    @PostMapping("/postWithApacheLibs")
    public String postWithApacheLibs(@RequestBody String message) {
        return String.format("Hello POST World!%n")
                + String.format("Sending data to Kafka...%n")
                + utils.SendMessage(env, message)
                + String.format("==============================================%n")
                + String.format("Receiving data from Kafka...%n")
                + KafkaUtils.ReadMessages(env);
    }

    // ====================================================================
    // Methods using only Java Spring Kafka libs
    // ====================================================================

}
