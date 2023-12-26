package com.example.personinfo.people.utils.mapper;

import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
public class PersonMapper {

    private List<PersonMapperStrategy> strategies;

    public PersonMapper(List<PersonMapperStrategy> strategies) {
        this.strategies = strategies;
    }

    public PersonDto mapToPersonDto(Person person) {
        for (PersonMapperStrategy strategy : strategies) {
            if (strategy.sup(person.getClass()))
                return strategy.map(person);
        }
        throw new IllegalArgumentException("Unknown person type");
    }
}
