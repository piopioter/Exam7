package com.example.personinfo.people.config.commandsdeserializer;

import com.example.personinfo.people.commands.*;
import com.example.personinfo.people.models.Retiree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class RetireeCommandStrategy implements CommandStrategy {

    @Override
    public boolean sup(String type) {
        return Retiree.class.getSimpleName().equalsIgnoreCase(type);
    }

    @Override
    public IPersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException {
        if (root.has("id"))
            return mapper.readValue(root.toString(), UpdateRetireeCommand.class);
        return mapper.readValue(root.toString(), CreateRetireeCommand.class);
    }
}
