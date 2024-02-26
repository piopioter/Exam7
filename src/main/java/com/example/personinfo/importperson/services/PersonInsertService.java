package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.models.PersonInsertStrategy;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PersonInsertService {

    private Map<String, PersonInsertStrategy> factories;

    public PersonInsertService(Map<String, PersonInsertStrategy> factories) {
        this.factories = factories;
    }

    public void insertPerson(String type, String[] parts, JdbcTemplate jdbcTemplate) {
        PersonInsertStrategy factory = factories.get(type.toLowerCase());
        if (factory == null) {
            throw new ResourceNotFoundException("Unknown person type");
        }
        factory.insert(jdbcTemplate, parts);

    }
}
