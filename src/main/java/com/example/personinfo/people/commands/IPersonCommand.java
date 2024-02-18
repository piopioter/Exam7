package com.example.personinfo.people.commands;

import com.example.personinfo.people.config.commandsdeserializer.PersonCommandDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PersonCommandDeserializer.class)
public interface IPersonCommand {
//    String getType();
}

