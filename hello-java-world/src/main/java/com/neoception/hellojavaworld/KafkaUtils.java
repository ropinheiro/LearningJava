package com.neoception.hellojavaworld;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@RequiredArgsConstructor
@Component
public class KafkaUtils {

    @Value("${KAFKA_TOPIC}")
    String topic;

    private final KafkaProducerConfig configs;

    // ====================================================================
    // Methods using Apache Kafka libs and Environment
    // ====================================================================
    public static Properties LoadConfig(Environment env) {
        Properties props = new Properties();

        // General
        props.put("bootstrap.servers", env.getProperty("bootstrap.servers"));
        props.put("security.protocol", env.getProperty("security.protocol"));
        props.put("sasl.jaas.config", env.getProperty("sasl.jaas.config"));
        props.put("sasl.mechanism", env.getProperty("sasl.mechanism"));

        // For Producer
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // For Consumer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka.groupId"));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    public static String SendMessage(Environment env, String message) {

        String topic = env.getProperty("kafka.topic");
        if( topic == null ) {
            return "Topic cannot be null!%n";
        }

        // Configure Kafka properties.
        final Properties props = LoadConfig(env);

        // Create a Producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Send the message
        final String[] result = {String.format("Writing to Topic: %s%nData:%n", topic)};
        producer.send(new ProducerRecord<>(topic, "neo", message), (m, e) -> {
            result[0] += String.format("| %s: %s -> ", "neo", message);
            if (e != null) {
                result[0] += "error%n";
            } else {
                result[0] += String.format("partition: [%d] @ offset %d%n", m.partition(), m.offset());
            }
        });

        producer.flush();
        producer.close();
        return result[0];
    }

    public static String SendMessages(Environment env) {
        String topic = env.getProperty("kafka.topic");
        Integer messageCount = Integer.parseInt( env.getProperty("kafka.messageCount") );

        if( topic == null ) {
            return OutputUtils.Line("Topic cannot be null!");
        }

        // Configure Kafka properties.
        final Properties props = LoadConfig(env);

        // Create a Producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Send some data
        final String[] result = {OutputUtils.Line(String.format("Writing to Topic: %s<br/>Data:", topic))};
        for (Long i = 0L; i < messageCount; i++) {
            String key = "neo";
            String record = "some_value_" + i.toString();

            producer.send(new ProducerRecord<>(topic, key, record), (m, e) -> {
                result[0] += String.format("| %s: %s -> ", key, record);
                if (e != null) {
                    result[0] += "error%n";
                } else {
                    result[0] += OutputUtils.Line(String.format("partition: [%d] @ offset %d", m.partition(), m.offset()));
                }
            });
        }

        producer.flush();
        producer.close();
        return result[0];
    }

    public static String ReadMessages(Environment env) {
        String topic = env.getProperty("kafka.topic");
        if( topic == null ) {
            return OutputUtils.Line("Topic cannot be null!");
        }

        // Configure Kafka properties.
        final Properties props = LoadConfig(env);

        // Create a Consumer
        final Consumer<String, String> consumer =
                new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        // Read data
        final String[] result = {OutputUtils.Line(String.format("Reading from Topic: %s<br/>Data:", topic))};
        try {
            Boolean hasMessages = false;
            do {
                hasMessages = false;
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofSeconds(5));
                result[0] += OutputUtils.Line("|------------------------");
                for (ConsumerRecord<String, String> record : records) {
                    hasMessages = true;
                    String key = record.key();
                    String value = record.value();
                    result[0] += OutputUtils.Line(String.format("| %s: %s", key, value));
                }
            } while (hasMessages);
        } finally {
            consumer.close();
        }

        return result[0];
    }

    // ====================================================================
    // Methods using only Java Spring Kafka libs
    // ====================================================================
    public String SendMessage(String message) {

        KafkaTemplate<String, String> kafkaTemplate =
                configs.kafkaTemplate();

        // Send the message
        final String[] result = {
                OutputUtils.Line(String.format("Writing to Topic: %s", topic))
                        + OutputUtils.Line("Data:")
        };
        result[0] += String.format("| %s: %s -> ", "neo", message);
        try {
            kafkaTemplate.send(topic, "neo", message);
            result[0] += OutputUtils.Line("success!");
        } catch (Exception ex) {
            result[0] += OutputUtils.Line("error!");
        }
        kafkaTemplate.flush();

        return result[0];
    }

}
