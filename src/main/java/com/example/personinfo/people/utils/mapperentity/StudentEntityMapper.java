package com.example.personinfo.people.utils.mapperentity;

import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentEntityMapper implements PersonEntityMapper {
    private ModelMapper mapper;

    public StudentEntityMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> Person map(T person) {
        return mapper.map(person, Student.class);
    }

    @Override
    public boolean sup(String type) {
        return Student.class.getSimpleName().equalsIgnoreCase(type);
    }
}
