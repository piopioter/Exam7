package com.example.personinfo.importperson.models;

import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.Person;

import java.util.HashMap;
import java.util.Map;

public class ProcessPersonFactory {
    private static final Map<String, PersonFactory> factories = new HashMap<>();

    static {
        factories.put("student", new StudentFactory());
        factories.put("employee", new EmployeeFactory());
        factories.put("retiree", new RetireeFactory());
    }

    public static Person createPerson(String type, String[] parts){
        PersonFactory factory = factories.get(type.toLowerCase());
        if(factory  == null){
            throw new ResourceNotFoundException("Unknown person type");
        }
        return factory.createPerson(parts);

    }
}
