package com.example.personinfo.people.config.commandsdeserializer;

import com.example.personinfo.people.commands.IPersonCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface CommandStrategy {

    boolean sup(String type);

    IPersonCommand create(JsonNode root, ObjectMapper mapper) throws JsonProcessingException;
}
