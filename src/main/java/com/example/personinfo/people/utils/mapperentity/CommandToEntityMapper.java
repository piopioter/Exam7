package com.example.personinfo.people.utils.mapperentity;

import com.example.personinfo.people.models.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandToEntityMapper {

    private List<PersonEntityMapper> personEntityMappers;

    public CommandToEntityMapper(List<PersonEntityMapper> personEntityMappers) {
        this.personEntityMappers = personEntityMappers;
    }

    public <T> Person mapToEntity(T object, String type) {
        for (PersonEntityMapper e : personEntityMappers) {
            if (e.sup(type))
                return e.map(object);
        }
        throw new IllegalArgumentException("Unknown person type");

    }
}
