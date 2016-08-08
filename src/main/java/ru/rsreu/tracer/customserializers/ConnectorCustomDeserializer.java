package ru.rsreu.tracer.customserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.rsreu.tracer.pojo.Connector;

import java.io.IOException;

public class ConnectorCustomDeserializer extends JsonDeserializer<Connector> {

    @Override
    public Connector deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String[] connectorParams = jsonParser.getText().split("_");
        return new Connector(Integer.parseInt(connectorParams[0]), Integer.parseInt(connectorParams[1]));
    }
}
