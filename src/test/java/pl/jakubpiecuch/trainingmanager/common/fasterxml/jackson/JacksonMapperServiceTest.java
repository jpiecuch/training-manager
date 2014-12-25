package pl.jakubpiecuch.trainingmanager.common.fasterxml.jackson;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JacksonMapperServiceTest {

    private static final Long ID = 12l;
    private static final String NAME = "name";
    private static final String JSON = "{\"id\":"+ ID +", \"name\":\""+ NAME +"\"}";
    private static final String JSON_WRONG = "{\"id\":"+ ID +", \"name\":\""+ NAME +"\", \"wrong\":\"error\"}";
    private static TestObject VALID_OBJECT = new TestObject();
    private JacksonMapperService mapperService = new JacksonMapperService();

    static class TestObject {
        public Long id;
        public String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject that = (TestObject) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }

    @Before
    public void setUp() {
        VALID_OBJECT.id = ID;
        VALID_OBJECT.name = NAME;
    }

    @Test
    public void testGetObject() throws Exception {
        TestObject object = mapperService.getObject(IOUtils.toInputStream(JSON), TestObject.class);
        assertEquals(VALID_OBJECT, object);
    }

    @Test(expected =  JsonMappingException.class)
    public void testGetObjectWithNullInput() throws Exception {
        mapperService.getObject(null, TestObject.class);
    }

    @Test(expected =  JsonMappingException.class)
    public void testGetObjectWithWrongClass() throws Exception {
        mapperService.getObject(IOUtils.toInputStream(JSON), String.class);
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void testGetObjectWithWrongJson() throws Exception {
        mapperService.getObject(IOUtils.toInputStream(JSON_WRONG), TestObject.class);
    }
}