package com.example.personinfo.importperson.models;

import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.Person;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class ProcessPersonFactory {

   private Map<String, PersonFactory> factories;


    public ProcessPersonFactory(Map<String, PersonFactory> factories) {
        this.factories = factories;
    }

    public Person createPerson(String type, String[] parts) {
        PersonFactory factory = factories.get(type.toLowerCase());
        if (factory == null) {
            throw new ResourceNotFoundException("Unknown person type");
        }
        return factory.createPerson(parts);

    }
}
