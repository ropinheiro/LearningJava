package com.packt.springboot.blogmania;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class BlogConfiguration {

    @Bean
    public Instant blogDate() {
        return Instant.now();
    }

}
