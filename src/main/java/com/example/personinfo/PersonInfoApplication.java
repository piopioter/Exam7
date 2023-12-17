package com.example.personinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class PersonInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonInfoApplication.class, args);
    }

}
