package com.example.personinfo.people.utils.mapperdto;

import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.Person;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EntityToDtoMapper {

    private List<PersonDtoMapper> strategies;

    public EntityToDtoMapper(List<PersonDtoMapper> strategies) {
        this.strategies = strategies;
    }

    public PersonDto mapEntityToDto(Person person) {
        for (PersonDtoMapper strategy : strategies) {
            if (strategy.sup(person.getClass()))
                return strategy.map(person);
        }
        throw new IllegalArgumentException("Unknown person type");
    }
}
