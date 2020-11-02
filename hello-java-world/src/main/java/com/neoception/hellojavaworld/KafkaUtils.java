package com.neoception.hellojavaworld;

// import org.apache.kafka.clients.producer.KafkaProducer;
// import org.apache.kafka.clients.producer.ProducerRecord;
// import org.apache.kafka.clients.producer.RecordMetadata;
// import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerConfig;
// import org.apache.kafka.clients.producer.Producer;

// import org.apache.kafka.clients.admin.AdminClient;
// import org.apache.kafka.clients.admin.AdminClientConfig;
// import org.apache.kafka.clients.admin.NewTopic;

// TODO: how to fix this?
// import io.confluent.examples.clients.cloud.model.DataRecord;

import java.io.FileInputStream;
// import java.io.IOException;
// import java.util.concurrent.ExecutionException;
// import org.apache.kafka.common.errors.TopicExistsException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
// import java.util.Collections;
// import java.util.Map;

public class KafkaUtils {

    public static String SendMessages() {
        // Load properties from a local configuration file
        final Properties props = LoadConfig("src/java.config");

        if( props == null ) {
            return "Unable to load configurations!";
        }
        // Create topic if needed
        final String topic = "PUT HERE TOPIC";

        // Add additional properties.
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonSerializer");

        // Producer<String, DataRecord> producer = new KafkaProducer<String, DataRecord>(props);

        return "Messages sent!";
    }


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
}
