package com.example.personinfo.people.config;

import com.example.personinfo.people.annotations.PersonType;
import com.example.personinfo.people.utils.mapper.PersonMapper;
import com.example.personinfo.people.utils.mapper.PersonMapperStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Configuration
public class BeansConfig {

    @Bean
    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public ObjectMapper createCustomObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(
                new SubTypesScanner(), new TypeAnnotationsScanner()).forPackages(""));
        Set<Class<?>> subtypes = reflections.getTypesAnnotatedWith(PersonType.class);

        for (Class<?> subType : subtypes) {
            PersonType annotation = subType.getAnnotation(PersonType.class);
            if (annotation != null) {
                String typeName = annotation.value().getSimpleName();
                objectMapper.registerSubtypes(new NamedType(subType, typeName));
            }

        }
        return objectMapper;
    }



}
