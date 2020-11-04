package com.neoception.envdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EnvDemoApplication implements CommandLineRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(EnvDemoApplication.class);

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) {
        logger.info("{}", "Java Home: " + env.getProperty("JAVA_HOME"));
        logger.info("{}", "App Name: " + env.getProperty("app.name"));
    }

    public static void main(String[] args) {
        SpringApplication.run(EnvDemoApplication.class, args);
    }
}
