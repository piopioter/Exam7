package com.example.personinfo.people.config.commandsdeserializer.createdeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.commands.CreateStudentCommand;
import com.example.personinfo.people.config.commandsdeserializer.createdeserializator.CreateCommandStrategy;
import com.example.personinfo.people.models.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCreateCommandStrategy implements CreateCommandStrategy {

    @Override
    public boolean sup(String type) {
        return Employee.class.getSimpleName().equalsIgnoreCase(type);
    }

    @Override
    public CreatePersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.readValue(root.toString(), CreateStudentCommand.class);
    }
}
