package com.example.personinfo.people.config.commandsdeserializer.updatedeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.commands.CreateStudentCommand;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.commands.UpdateStudentCommand;
import com.example.personinfo.people.models.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentUpdateCommandStrategy implements UpdateCommandStrategy {

    @Override
    public boolean sup(String type) {
        return Student.class.getSimpleName().equalsIgnoreCase(type);
    }

    @Override
    public UpdatePersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.readValue(root.toString(), UpdateStudentCommand.class);
    }
}
