package com.example.personinfo.people.utils.mapper;

import com.example.personinfo.people.dto.EmployeeDto;
import com.example.personinfo.people.dto.PersonDto;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.models.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper implements PersonMapperStrategy {

    private ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean sup(Class<?> clazz) {
        return Employee.class.equals(clazz);
    }

    @Override
    public PersonDto map(Person person) {
        return modelMapper.map(person, EmployeeDto.class);
    }
}
