package com.example.personinfo.importperson.models;

import com.example.personinfo.people.models.Person;

public interface PersonFactory {

    Person createPerson(String[] parts);
}
