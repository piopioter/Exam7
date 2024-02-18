package com.example.personinfo.people.utils.mapperdto;

import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.Person;

public interface PersonDtoMapper {
    boolean sup(Class<?> clazz);
    PersonDto map(Person person);
}
