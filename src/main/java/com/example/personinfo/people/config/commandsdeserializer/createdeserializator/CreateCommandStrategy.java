package com.example.personinfo.people.config.commandsdeserializer.createdeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface CreateCommandStrategy {

    boolean sup(String type);

    CreatePersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException;
}
