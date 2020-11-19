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
        props.put("sasl.mechanism", env.getProperty("kafka.properties.sasl.mechanism"));
        props.put("security.protocol", env.getProperty("kafka.properties.security.protocol"));
        props.put("bootstrap.servers", env.getProperty("SPRING_KAFKA_BOOTSTRAP_SERVERS"));
        props.put("sasl.jaas.config", env.getProperty("SPRING_KAFKA_PROPERTIES_SASL_JAAS_CONFIG"));

        // For Producer, from dev.env
        props.put(ProducerConfig.ACKS_CONFIG, env.getProperty("SPRING_KAFKA_ACKS_CONFIG"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, env.getProperty("SPRING_KAFKA_KEY_SERIALIZER_CLASS_CONFIG"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, env.getProperty("SPRING_KAFKA_VALUE_SERIALIZER_CLASS_CONFIG"));

        // For Consumer, from dev.env
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, env.getProperty("kafka.properties.auto.offset.reset.config"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, env.getProperty("SPRING_KAFKA_KEY_DESERIALIZER_CLASS_CONFIG"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, env.getProperty("SPRING_KAFKA_VALUE_DESERIALIZER_CLASS_CONFIG"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("SPRING_KAFKA_CONSUMER_GROUP_ID"));

        return props;
    }

    public static String SendMessage(Environment env, String message) {

        String topic = env.getProperty("KAFKA_TOPIC");
        if (topic == null) {
            return "Topic cannot be null!<br/>";
        }

        // Configure Kafka properties.
        final Properties props = LoadConfig(env);

        // Create a Producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Send the message
        final String[] result = {
                OutputUtils.Line(String.format("Writing to Topic: %s", topic))
                        + OutputUtils.Line("Data:")
        };

        producer.send(new ProducerRecord<>(topic, "neo", message), (m, e) -> {
            result[0] += String.format("| %s: %s -> ", "neo", message);
            if (e != null) {
                result[0] += OutputUtils.Line("error");
            } else {
                result[0] += OutputUtils.Line(
                        String.format("partition: [%d] @ offset %d", m.partition(), m.offset()));
            }
        });

        producer.flush();
        producer.close();
        return result[0];
    }

    public static String ReadMessages(Environment env) {
        String topic = env.getProperty("KAFKA_TOPIC");
        if (topic == null) {
            return OutputUtils.Line("Topic cannot be null!");
        }

        // Configure Kafka properties.
        final Properties props = LoadConfig(env);

        // Create a Consumer
        final Consumer<String, String> consumer =
                new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        // Read data
        final String[] result = {
                OutputUtils.Line(String.format("Reading from Topic: %s", topic))
                        + OutputUtils.Line("Data:")
        };
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
