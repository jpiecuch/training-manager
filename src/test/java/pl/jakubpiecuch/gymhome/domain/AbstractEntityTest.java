package pl.jakubpiecuch.gymhome.domain;

import org.apache.commons.lang3.SerializationUtils;

import static org.junit.Assert.*;

/**
 * Created by Rico on 2015-03-08.
 */
public abstract class AbstractEntityTest<T extends CommonEntity> {

    protected abstract T getEntity();

    protected abstract void assertEntity();

    protected void testEntity() {
        assertEntity();
        testHashCode();
        testEquals();
        testToString();
    }

    protected void testHashCode() {
        assertEquals(getEntity().hashCode(), SerializationUtils.clone(getEntity()).hashCode());
    }

    protected void testEquals() {
        assertTrue(getEntity().equals(SerializationUtils.clone(getEntity())));
        assertFalse(getEntity().equals("not the same type"));
        assertFalse(getEntity().equals(null));
        assertTrue(getEntity().equals(getEntity()));
    }

    protected void testToString() {
        assertNotNull(getEntity().toString());
    }
}
