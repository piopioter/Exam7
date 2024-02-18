package com.example.personinfo.importperson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class Config {

    @Bean
    public ReentrantLock createReentrantLock(){
        return new ReentrantLock();
    }
}
