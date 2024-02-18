package com.example.personinfo.people.utils.mapperentity;

import com.example.personinfo.people.models.Person;

public interface PersonEntityMapper {

    <T> Person map(T person);

    boolean sup(String type);
}
