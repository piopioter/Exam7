package com.example.personinfo.people.config.commandsdeserializer.updatedeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface UpdateCommandStrategy {
    boolean sup(String type);

    UpdatePersonCommand  create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException;
}
