package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ExerciseDaoImplTest extends BaseDAOTestCase {

    private static final long WORKOUT_ID = 1l;
    private static final int EXERCISE_COUNT = 2;
    private static final Map<Integer, Integer> SETS = new HashMap<Integer, Integer>() {
        {
            put(0, 12);
            put(1, 12);
            put(2, 12);
            put(3, 12);
        }
    };

    @Autowired
    private ExerciseDao exerciseDao;
    
    @Test
    public void testFindByWorkoutId() {
        List<Exercise> list = exerciseDao.findByWorkoutId(WORKOUT_ID);

        assertEquals(EXERCISE_COUNT, list.size());

        Exercise exercise = list.get(0);

        assertEquals(SETS, exercise.getSets());
    }
}