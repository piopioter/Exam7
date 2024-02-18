package com.example.personinfo.people.config.commandsdeserializer;

import com.example.personinfo.people.commands.CreateStudentCommand;
import com.example.personinfo.people.commands.IPersonCommand;
import com.example.personinfo.people.commands.UpdateStudentCommand;
import com.example.personinfo.people.models.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCommandStrategy implements CommandStrategy {

    @Override
    public boolean sup(String type) {
        return Employee.class.getSimpleName().equalsIgnoreCase(type);
    }

    @Override
    public IPersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException {
        if (root.has("id"))
            return mapper.readValue(root.toString(), UpdateStudentCommand.class);
        return mapper.readValue(root.toString(), CreateStudentCommand.class);

    }
}
