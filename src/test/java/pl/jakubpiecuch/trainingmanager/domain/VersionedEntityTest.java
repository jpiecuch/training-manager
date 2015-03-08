package pl.jakubpiecuch.trainingmanager.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class VersionedEntityTest extends AbstractEntityTest<VersionedEntity> {

    private static final Long ID = 1l;
    private static final Date UPDATED = new Date();
    private static final Date CREATED = new Date();
    private VersionedEntity comment = new VersionedEntity();

    @Before
    public void setUp() {
        comment = new VersionedEntity(ID);
        comment.setId(ID);
        comment.setUpdated(UPDATED);
        comment.setCreated(CREATED);
    }

    @Override
    protected VersionedEntity getEntity() {
        return comment;
    }

    @Test
    public void testExerciseComment() {
        testEntity();
    }

    @Override
    protected void assertEntity() {
        assertEquals(ID, comment.getId());
        assertEquals(UPDATED, comment.getUpdated());
        assertEquals(CREATED, comment.getCreated());
    }

}