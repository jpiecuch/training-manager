package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.BaseUnitDaoTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import static org.junit.Assert.*;

/**
 * Created by Rico on 2015-06-13.
 */
public class UserWorkoutDaoImplTest extends BaseUnitDaoTestCase {

    @Autowired
    private RepoDao<UserWorkout, UserWorkoutCriteria> userWorkoutDao;

    @Before
    public void setUp() {
        addUserToContext();
    }

    @Test
    public void testFindByCriteriaNull() {
        PageResult<UserWorkout> page = userWorkoutDao.findByCriteria(null);

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());
    }

    @Test
    public void testFindByCriteriaEmpty() {
        PageResult<UserWorkout> page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en"));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

    }

    @Test
    public void testFindByCriteriaDateRangeRestriction() {
        PageResult<UserWorkout> page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new LocalDate(2014, 12, 7).toDate(), new LocalDate(2014, 12, 7).toDate()));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new LocalDate(2014, 12, 7).toDate(), new LocalDate(2014, 12, 8).toDate()));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new LocalDate(2014, 12, 6).toDate(), new LocalDate(2014, 12, 7).toDate()));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new LocalDate(2014, 12, 6).toDate(), new LocalDate(2014, 12, 8).toDate()));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new LocalDate(2014, 12, 8).toDate(), new LocalDate(2014, 12, 8).toDate()));

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new LocalDate(2014, 12, 6).toDate(), new LocalDate(2014, 12, 6).toDate()));

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());

    }

    @Test
    public void testFindByCriteriaStateRestriction() {
        PageResult<UserWorkout> page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addStateRestrictions(UserWorkout.State.PLANNED));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addStateRestrictions(UserWorkout.State.COMPLETED));

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addStateRestrictions(UserWorkout.State.IN_PROGRESS));

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addStateRestrictions(UserWorkout.State.REJECTED));

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());
    }

    @Test
    public void testFindByCriteriaExcludedIdRestriction() {
        PageResult<UserWorkout> page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addExcludedIdRestriction(2l));

        assertEquals(1l, page.getCount());
        assertFalse(page.getResult().isEmpty());

        page = userWorkoutDao.findByCriteria(new UserWorkoutCriteria("en").addExcludedIdRestriction(1l));

        assertEquals(0l, page.getCount());
        assertTrue(page.getResult().isEmpty());
    }

}