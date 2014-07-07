package pl.jakubpiecuch.trainingmanager.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;


public class DayExercisesDaoTest extends AbstractBaseTest {
    
    @Autowired
    private DayExercisesDao dayExercisesDao;

    @Test
    public void testFindById() {
        DayExercises d = dayExercisesDao.findById(69l);
        
        assertEquals(2l, (long) d.getHours());
        assertEquals(23l, (long) d.getMinutes());
        assertEquals(10l, (long) d.getSeconds());
        
    }
    
}
