package com.example.personinfo.people.utils.mapperentity;

import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.models.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeEntityMapper implements PersonEntityMapper {

    private ModelMapper mapper;

    public EmployeeEntityMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> Person map(T person) {
        return mapper.map(person, Employee.class);
    }

    @Override
    public boolean sup(String type) {
        return Employee.class.getSimpleName().equalsIgnoreCase(type);
    }
}
