package com.example.personinfo;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PersonInfoApplication {

    public static void main(String[] args) {
       SpringApplication.run(PersonInfoApplication.class, args);



    }

}
