package com.com3014.group1.projectmatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class ApplicationEntry {

    /**
     * Application Entry
     * 
     * @param args
     *      Empty Arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntry.class, args);
    }

}
