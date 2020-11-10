# LearningJava
Dump place for my projects where I'm learning JAVA

## blogmania

TODO: describe

## env-demo

Playing around with environment variables

Based on learnings from http://zetcode.com/springboot/environment/

## hello-java-world

Web App returning Hello World string.

Based on learnings from https://spring.io/guides/gs/spring-boot/#scratch

It was extended to produce/consume messages to/from a topic in a Kafka cluster.

Based on code from https://github.com/confluentinc/examples/tree/6.0.0-post/clients/cloud/java/src/main/java/io/confluent/examples/clients/cloud

It was extended to use environment variables and Docker.

Use the following commands:

> docker build --tag hello-java-world:1.0 .

> docker run -p 8081:9000 --env-file=dev.env hello-java-world:1.0

Note that the web app runs in the port 9000 of the container (see dev.env file), and then we expose it in localhost:8081 (see docker command above).

## titlecase-converter

Desktop App that converts some input text to a Title Case format.

Based on learnings from https://app.pluralsight.com/library/courses/setting-up-java-development-environment

When you build in IntelliJ, the jar file is generated in

> \out\artifacts\titlecase_converter_jar

Execute it by opening a Command Prompt, going to that folder and running:

> java -jar titlecase-converter.jar
