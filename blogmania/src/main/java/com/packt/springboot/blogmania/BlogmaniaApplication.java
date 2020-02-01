package com.packt.springboot.blogmania;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.print.StreamPrintService;

@SpringBootApplication
public class BlogManiaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(BlogManiaApplication.class, args);

		System.out.println("-----------------------------------------------------");
		System.out.println("-- Blog's Beans: ");
		System.out.println("-----------------------------------------------------");
        for (String s : context.getBeanDefinitionNames()) {
            if (s.contains("blog"))
                System.out.println(">> " + s);
        }
		System.out.println("-----------------------------------------------------");
    }

}
