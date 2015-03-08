package pl.jakubpiecuch.trainingmanager.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class BaseCommentTest extends AbstractEntityTest<BaseComment> {

    private static final Long ID = 1l;
    private static final Date UPDATED = new Date();
    private static final Date CREATED = new Date();
    private static final Long ACCOUNT_ID = 2l;
    private static final Account ACCOUNT = new Account(ACCOUNT_ID);
    private static final String COMMENT = "comment";
    private static final Long COMMENTED_ID = 3l;
    private static final Description COMMENTED = new Description(COMMENTED_ID);
    private BaseComment comment = new BaseComment();

    @Before
    public void setUp() {
        comment = new BaseComment(ID);
        comment.setId(ID);
        comment.setUpdated(UPDATED);
        comment.setCreated(CREATED);
        comment.setAccount(ACCOUNT);
        comment.setComment(COMMENT);
        comment.setCommented(COMMENTED);
    }

    @Override
    protected BaseComment getEntity() {
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
        assertEquals(ACCOUNT, comment.getAccount());
        assertEquals(COMMENT, comment.getComment());
        assertEquals(COMMENTED, comment.getCommented());
    }

}