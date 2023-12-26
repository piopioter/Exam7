package com.example.personinfo.people.utils.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
public class PersonMapperConfig {

    private List<PersonMapperStrategy> strategies;

    public PersonMapperConfig(List<PersonMapperStrategy> strategies) {
        this.strategies = strategies;
    }

    @Bean
    public PersonMapper createPersonMapper(){
        return new PersonMapper(strategies);
    }
}
