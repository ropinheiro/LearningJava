package com.neoception.hellojavaworld;

import org.apache.kafka.clients.producer.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static String SendMessages() {
        // Load properties from a local configuration file
        final Properties props = LoadConfig("src/java.config");

        if( props == null ) {
            return "Unable to load configurations!";
        }
        // Define tropic
        final String topic = "PUT HERE TOPIC";

        // Add additional properties.
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // Create a Producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Send some data
        final Long numMessages = 10L;
        final String[] result = {String.format("Topic: %s%nData:%n", topic)};
        for (Long i = 0L; i < numMessages; i++) {
            String key = "neo";
            String record = "some_value_" + i.toString();

            producer.send(new ProducerRecord<String, String>(topic, key, record), (m, e) -> {
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

    public static String ReadMessages() {
        return "TODO";
    }
}
