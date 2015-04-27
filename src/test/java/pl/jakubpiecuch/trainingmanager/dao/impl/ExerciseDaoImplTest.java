package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExerciseDaoImplTest extends BaseDAOTestCase {

    private static final long WORKOUT_ID = 1l;
    private static final int EXERCISE_COUNT = 2;
    private static final String[] SETS = new String[] {"12","12","12","12"};

    @Autowired
    private ExerciseDao exerciseDao;
    
    @Test
    public void testFindByWorkoutId() {
        List<Exercise> list = exerciseDao.findByParentId(WORKOUT_ID);

        assertEquals(EXERCISE_COUNT, list.size());

        Exercise exercise = list.get(0);

        assertEquals(SETS, exercise.getSets());
    }
}