package pl.jakubpiecuch.gymhome.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonEntityTest extends AbstractEntityTest<CommonEntity> {

    private static final Long ID = 1l;
    private CommonEntity entity = new Account();

    @Before
    public void setUp() {
        entity = new CommonEntity(ID);
        entity.setId(ID);
    }

    @Override
    public CommonEntity getEntity() {
        return entity;
    }

    @Override
    public void assertEntity() {
        assertEquals(ID, entity.getId());
    }

    @Test
    public void testCommonEntity() {
        testEntity();
    }
}