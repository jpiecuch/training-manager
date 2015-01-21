package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.WorkoutDao;
import pl.jakubpiecuch.trainingmanager.domain.Workout;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WorkoutDaoImplTest extends BaseDAOTestCase {

    private static final long PHASE_ID = 1l;
    private static final int WORKOUT_COUNT = 1;

    @Autowired
    private WorkoutDao workoutDao;

    @Test
    public void testFindByPhaseId() {
        List<Workout> list = workoutDao.findByParentId(PHASE_ID);

        assertEquals(WORKOUT_COUNT, list.size());

        Workout workout = list.get(0);

        assertEquals(Workout.WeekDay.MONDAY, workout.getWeekDay());
    }

}