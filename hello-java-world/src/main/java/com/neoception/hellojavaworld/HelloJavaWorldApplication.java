package com.neoception.hellojavaworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

@SpringBootApplication
public class HelloJavaWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloJavaWorldApplication.class, args);
	}

}
