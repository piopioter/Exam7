package com.example.personinfo.people.utils.mapper;

import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.dto.RetireeDto;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Retiree;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RetireeMapper implements PersonMapperStrategy {

    private ModelMapper modelMapper;

    public RetireeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean sup(Class<?> clazz) {
        return Retiree.class.equals(clazz);
    }

    @Override
    public PersonDto map(Person person) {
        return modelMapper.map(person, RetireeDto.class);
    }
}
