package pl.jakubpiecuch.trainingmanager.common.fasterxml.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MapDeserializerTest {

    private MapDeserializer deserializer = new MapDeserializer();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String ID_0 = "0";
    private static final String VAL_0 = "2";
    private static final String ID_1 = "1";
    private static final String VAL_1 = "3";
    private static final String JSON = "{\""+ID_0+"\":\""+VAL_0+"\", \""+ID_1+"\":\""+VAL_1+"\"}";
    private static final String JSON_WITH_CHILDREN = "{\""+ID_0+"\":\""+VAL_0+"\", \""+ID_1+"\":{\"id\":\"4\"}}";
    private static final Map<String, String> JSON_MAP = new HashMap<String, String>() {
        {
            put(ID_0, VAL_0);
            put(ID_1, VAL_1);
        }
    };

    @Test
    public void testDeserialize() throws Exception {
        assertEquals(JSON_MAP, deserializer.deserialize(objectMapper.treeAsTokens(objectMapper.readTree(JSON)), null));
    }

    @Test(expected = NullPointerException.class)
    public void testDeserializeNullJsonParser() throws Exception {
        deserializer.deserialize(null, null);
    }

    @Test(expected = JsonParseException.class)
    public void testDeserializeStringJson() throws Exception {
        deserializer.deserialize(objectMapper.treeAsTokens(objectMapper.readTree("test")), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeArrayJson() throws Exception {
        deserializer.deserialize(objectMapper.treeAsTokens(objectMapper.readTree("[]")), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeJsonWithChildren() throws Exception {
        deserializer.deserialize(objectMapper.treeAsTokens(objectMapper.readTree(JSON_WITH_CHILDREN)), null);
    }
}