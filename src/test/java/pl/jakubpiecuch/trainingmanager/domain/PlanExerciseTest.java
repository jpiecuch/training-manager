package pl.jakubpiecuch.trainingmanager.domain;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class PlanExerciseTest extends TestCase {

    @Test
    public void testPlanExercise() {
        PlanExercise day = new PlanExercise();
        day.setReps("1;2;3;4");

        Assert.assertEquals(4, day.getSets().size());

        day.getSets().put(4, 5);

        Assert.assertEquals(5, day.getSets().size());
        Assert.assertEquals("1;2;3;4;5", day.getReps());

        day.getSets().remove(2);

        Assert.assertEquals(4, day.getSets().size());
        Assert.assertEquals("1;2;4;5", day.getReps());

        day.setReps(null);

        day.setSets(new HashMap<Integer, Integer>() {
            {
                put(0, 12);
                put(1, 23);
                put(2, 34);
            }
        });

        Assert.assertEquals(3, day.getSets().size());
        Assert.assertEquals("12;23;34", day.getReps());
    }
}