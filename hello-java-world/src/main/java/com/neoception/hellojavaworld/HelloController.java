package com.neoception.hellojavaworld;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestController
public class HelloController {

    private final Environment env;

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
                + useApacheLibs("Using Apache Kafka libs", "123" );
    }

    // To be used with Postman: post something.
    @PostMapping("/postWithApacheLibs")
    public String postWithApacheLibs(@RequestBody String message) {
        return OutputUtils.UseNewLinesInsteadOfHtmlBreaks(
                useApacheLibs("Hello POST World!", message));
    }

    private String useApacheLibs(String title, String message) {
        return OutputUtils.Line(title)
                + OutputUtils.Line("Sending data to Kafka...")
                + KafkaUtils.SendMessage(env, message)
                + OutputUtils.Line(OutputUtils.Bar)
                + OutputUtils.Line("Receiving data from Kafka...")
                + KafkaUtils.ReadMessages(env);
    }

    // ====================================================================
    // Methods using only Java Spring Kafka libs
    // ====================================================================
    @RequestMapping("/kafkaWithSpringLibs")
    public String kafkaWithSpringLibs() {
        return OutputUtils.ReturnToIndex()
                + useSpringLibs( "Using Java Spring Kafka libs", "123");
    }

    // To be used with Postman: post something.
    @PostMapping("/postWithSpringLibs")
    public String postWithSpringLibs(@RequestBody String message) {
        return OutputUtils.UseNewLinesInsteadOfHtmlBreaks(
                useSpringLibs( "Hello POST World!", message));
    }

    private String useSpringLibs(String title, String message) {
        return OutputUtils.Line(title)
                + OutputUtils.Line("Sending data to Kafka...")
                + utils.SendMessage(message);
    }

}
