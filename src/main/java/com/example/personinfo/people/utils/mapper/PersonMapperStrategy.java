package com.example.personinfo.people.utils.mapper;

import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.Person;

public interface PersonMapperStrategy {
    boolean sup(Class<?> clazz);
    PersonDto map(Person person);
}
