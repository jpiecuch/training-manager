package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Before;
import org.junit.Test;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

import static org.junit.Assert.*;

public class PlanConverterTest {

    private static final Long ID = 1l;
    private static final Plan.Goal GOAL = Plan.Goal.MUSCLES;
    private static final String NAME = "name";
    private static PlanDto PLAN_FLOW = new PlanDto();
    private static Plan PLAN = new Plan();

    private static final PlanConverter CONVERTER = new PlanConverter();


    @Before
    public void setUp() {
        PLAN_FLOW.setId(ID);
        PLAN_FLOW.setGoal(GOAL);
        PLAN_FLOW.setName(NAME);

        PLAN.setId(ID);
        PLAN.setGoal(GOAL);
        PLAN.setName(NAME);
    }

    @Test
    public void testToFlowObject() throws Exception {
        PlanDto flow = CONVERTER.toFlowObject(PLAN);
        assertEquals(flow, PLAN_FLOW);
    }

    @Test
    public void testFromFlowObject() throws Exception {
        Plan plan = CONVERTER.fromFlowObject(PLAN_FLOW);
        assertEquals(plan, PLAN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToFlowObjectNull() throws Exception {
        CONVERTER.toFlowObject(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromFlowObjectNull() throws Exception {
        CONVERTER.fromFlowObject(null);
    }
}