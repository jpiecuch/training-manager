package pl.jakubpiecuch.trainingmanager.common.fasterxml.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapDeserializer extends JsonDeserializer<Map> {

        @Override
        public Map deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Map result = new HashMap();
            jsonParser.nextToken();
            while (!jsonParser.nextToken().isStructEnd()) {
                result.put(jsonParser.getCurrentName(), jsonParser.getValueAsString());
            }
            return result;
        }
}
