package com.masai;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThingsBoardIntegrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThingsBoardIntegrationApplication.class, args);
    }
}