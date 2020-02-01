package com.packt.springboot.blogmania;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.print.StreamPrintService;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class BlogManiaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(BlogManiaApplication.class, args);

        System.out.println("-----------------------------------------------------");
        System.out.println("-- Blog's Beans: ");
        System.out.println("-----------------------------------------------------");

        Arrays.stream(context.getBeanDefinitionNames())
                .filter(s -> s.contains(("blog")))
                .sorted()
                .forEach(System.out::println);
        // I would like to prefix output lines with ">> ".
        // How can I achieve this using .forEach(System.out::println)?

        System.out.println("-----------------------------------------------------");
        System.out.println("-- BeanData's list: ");
        System.out.println("-----------------------------------------------------");

        ArrayList<BeanData> beanDataList = new ArrayList<>();

        for (String s : context.getBeanDefinitionNames()) {
            if (s.contains("blog") || s.contains("bean")) {
                beanDataList.add(new BeanData(s, context.getBean(s).getClass().getCanonicalName()));
            }
        }

        for (BeanData beanData : beanDataList) {

            StringBuilder sb = new StringBuilder();
            sb.append(">> Name: ");
            sb.append(beanData.getBean());
            sb.append("\n   Class: ");
            sb.append(beanData.getBeanClass());

            System.out.println(sb.toString());
        }

        System.out.println("-----------------------------------------------------");
    }

}
