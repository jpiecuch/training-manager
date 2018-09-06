package pl.jakubpiecuch.gymhome.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ExerciseCommentTest extends AbstractEntityTest<ExerciseComment> {

    private static final Long ID = 1l;
    private static final Date UPDATED = new Date();
    private static final Date CREATED = new Date();
    private static final Long ACCOUNT_ID = 2l;
    private static final Account ACCOUNT = new Account(ACCOUNT_ID);
    private static final String COMMENT = "comment";
    private static final Long COMMENTED_ID = 3l;
    private static final Description COMMENTED = new Description(COMMENTED_ID);
    private ExerciseComment exerciseComment = new ExerciseComment();

    @Before
    public void setUp() {
        exerciseComment = new ExerciseComment(ID);
        exerciseComment.setId(ID);
        exerciseComment.setUpdated(UPDATED);
        exerciseComment.setCreated(CREATED);
        exerciseComment.setAccount(ACCOUNT);
        exerciseComment.setComment(COMMENT);
        exerciseComment.setCommented(COMMENTED);
    }

    @Override
    protected ExerciseComment getEntity() {
        return exerciseComment;
    }

    @Test
    public void testExerciseComment() {
        testEntity();
    }

    @Override
    protected void assertEntity() {
        assertEquals(ID, exerciseComment.getId());
        assertEquals(UPDATED, exerciseComment.getUpdated());
        assertEquals(CREATED, exerciseComment.getCreated());
        assertEquals(ACCOUNT, exerciseComment.getAccount());
        assertEquals(COMMENT, exerciseComment.getComment());
        assertEquals(COMMENTED, exerciseComment.getCommented());
    }
}