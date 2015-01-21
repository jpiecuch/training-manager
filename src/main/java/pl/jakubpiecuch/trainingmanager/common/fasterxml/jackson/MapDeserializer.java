package pl.jakubpiecuch.trainingmanager.common.fasterxml.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapDeserializer extends JsonDeserializer<Map> {

        @Override
        public Map deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Map result = new HashMap();
            JsonToken token = jsonParser.nextToken();
            Assert.isTrue(JsonToken.START_OBJECT.id() == token.id());
            token = jsonParser.nextToken();
            while (!token.isStructEnd()) {
                Assert.isTrue(!token.isStructStart());
                result.put(jsonParser.getCurrentName(), jsonParser.getValueAsString());
                token = jsonParser.nextToken();
            }
            return result;
        }
}
