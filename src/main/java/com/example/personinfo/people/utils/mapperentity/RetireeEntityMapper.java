package com.example.personinfo.people.utils.mapperentity;

import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Retiree;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RetireeEntityMapper implements PersonEntityMapper {

    private ModelMapper mapper;

    public RetireeEntityMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> Person map(T person) {
        return mapper.map(person, Retiree.class);
    }

    @Override
    public boolean sup(String type) {
        return Retiree.class.getSimpleName().equalsIgnoreCase(type);
    }
}
