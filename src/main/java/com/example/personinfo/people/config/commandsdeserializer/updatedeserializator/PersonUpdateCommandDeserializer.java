package com.example.personinfo.people.config.commandsdeserializer.updatedeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.config.commandsdeserializer.createdeserializator.CreateCommandStrategy;
import com.example.personinfo.people.exceptions.UnsupportedCommandException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class PersonUpdateCommandDeserializer  extends JsonDeserializer<UpdatePersonCommand> {

    private List<UpdateCommandStrategy> strategies;

    public PersonUpdateCommandDeserializer(List<UpdateCommandStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public UpdatePersonCommand deserialize(JsonParser jsonParser, DeserializationContext dCtx) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);

        if (!root.has("type"))
            throw new UnsupportedCommandException("Missing type field");

        for (UpdateCommandStrategy strategy : strategies) {
            if (strategy.sup(root.get("type").asText()))
                return strategy.create(root, mapper);
        }
        throw new UnsupportedCommandException("Unknown type");
    }
}
