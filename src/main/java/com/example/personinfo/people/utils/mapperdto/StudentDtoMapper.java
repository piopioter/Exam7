package com.example.personinfo.people.utils.mapperdto;

import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.dto.StudentDto;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentDtoMapper implements PersonDtoMapper {

    private ModelMapper modelMapper;

    public StudentDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean sup(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public PersonDto map(Person person) {
        return modelMapper.map(person, StudentDto.class);
    }
}
