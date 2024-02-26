package com.example.personinfo.people.config.commandsdeserializer.updatedeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.commands.CreateRetireeCommand;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.commands.UpdateRetireeCommand;
import com.example.personinfo.people.models.Retiree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class RetireeUpdateCommandStrategy implements UpdateCommandStrategy {
    @Override
    public boolean sup(String type) {
        return Retiree.class.getSimpleName().equalsIgnoreCase(type);
    }

    @Override
    public UpdatePersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.readValue(root.toString(), UpdateRetireeCommand.class);
    }
}
