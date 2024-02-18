package com.example.personinfo.people.config.commandsdeserializer;

import com.example.personinfo.people.commands.IPersonCommand;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PersonCommandDeserializer extends JsonDeserializer<IPersonCommand> {

    private List<CommandStrategy> strategies;

    public PersonCommandDeserializer(List<CommandStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public IPersonCommand deserialize(JsonParser jsonParser, DeserializationContext dCtx) throws IOException {

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);

            for (CommandStrategy strategy : strategies) {
                if (strategy.sup(root.get("type").asText()))
                    return strategy.create(root, mapper);
            }
        return null;
    }
}
