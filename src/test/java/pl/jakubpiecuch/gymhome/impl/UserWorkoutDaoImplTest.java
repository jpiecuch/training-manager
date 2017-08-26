package pl.jakubpiecuch.gymhome.impl;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.gymhome.BaseUnitDaoTestCase;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.domain.UserWorkout;
import pl.jakubpiecuch.gymhome.service.user.workout.session.UserWorkoutCriteria;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rico on 2015-06-13.
 */
public class UserWorkoutDaoImplTest extends BaseUnitDaoTestCase {

    @Autowired
    private RepoDao<UserWorkout, UserWorkoutCriteria> userWorkoutDao;

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

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList("account", "date", "workout", "state");
    }

    @Override
    protected CoreDao getDao() {
        return userWorkoutDao;
    }

    @Override
    protected UserWorkout getEntity() {
        return new UserWorkout();
    }

}