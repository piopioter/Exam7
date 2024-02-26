package com.example.personinfo.people.config.commandsdeserializer.createdeserializator;

import com.example.personinfo.people.commands.CreatePersonCommand;
import com.example.personinfo.people.config.commandsdeserializer.createdeserializator.CreateCommandStrategy;
import com.example.personinfo.people.exceptions.UnsupportedCommandException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PersonCreateCommandDeserializer extends JsonDeserializer<CreatePersonCommand> {

    private List<CreateCommandStrategy> strategies;

    public PersonCreateCommandDeserializer(List<CreateCommandStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public CreatePersonCommand deserialize(JsonParser jsonParser, DeserializationContext dCtx) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);

        if (!root.has("type"))
            throw new UnsupportedCommandException("Missing type field");

        for (CreateCommandStrategy strategy : strategies) {
            if (strategy.sup(root.get("type").asText()))
                return strategy.create(root, mapper);
        }
        throw new UnsupportedCommandException("Unknown type");
    }
}
