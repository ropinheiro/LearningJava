package com.neoception.hellojavaworld;

import org.springframework.core.env.Environment;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaUtils {

    public static Properties LoadConfig(final String configFile) {
        if (!Files.exists(Paths.get(configFile))) {
            // TODO: handle this
            return null;
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        catch (Exception exception){
            // TODO: handle this
            return null;
        }
        return cfg;
    }

    public static String SendMessages(Environment env) {
        String topic = env.getProperty("kafka.topic");
        Integer messageCount = Integer.parseInt( env.getProperty("kafka.messageCount") );

        if( topic == null ) {
            return "Topic cannot be null!%n";
        }

        // Load properties from a local configuration file
        final Properties props = LoadConfig("src/java.config");
        if( props == null ) {
            return "Unable to load configurations!%n";
        }

        // Add additional properties.
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // Create a Producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Send some data
        final String[] result = {String.format("Writing to Topic: %s%nData:%n", topic)};
        for (Long i = 0L; i < messageCount; i++) {
            String key = "neo";
            String record = "some_value_" + i.toString();

            producer.send(new ProducerRecord<>(topic, key, record), (m, e) -> {
                result[0] += String.format("| %s: %s -> ", key, record);
                if (e != null) {
                    result[0] += "error%n";
                } else {
                    result[0] += String.format("partition: [%d] @ offset %d%n", m.partition(), m.offset());
                }
            });
        }

        producer.flush();
        producer.close();
        return result[0];
    }

    public static String ReadMessages(Environment env) {
        String topic = env.getProperty("kafka.topic");
        String groupId = env.getProperty("kafka.groupId");

        if( topic == null ) {
            return "Topic cannot be null!%n";
        }
        if( groupId == null ) {
            return "GroupId cannot be null!%n";
        }

        // Load properties from a local configuration file
        final Properties props = LoadConfig("src/java.config");
        if( props == null ) {
            return "Unable to load configurations!%n";
        }

        // Add additional properties.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Create a Consumer
        final Consumer<String, String> consumer =
                new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        // Read data
        final String[] result = {String.format("Reading from Topic: %s%nData:%n", topic)};
        try {
            Boolean hasMessages = false;
            do {
                hasMessages = false;
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofSeconds(5));
                result[0] += String.format("|------------------------%n");
                for (ConsumerRecord<String, String> record : records) {
                    hasMessages = true;
                    String key = record.key();
                    String value = record.value();
                    result[0] += String.format("| %s: %s%n", key, value);
                }
            } while (hasMessages);
        } finally {
            consumer.close();
        }

        return result[0];
    }
}
