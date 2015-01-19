package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PlanConverterTest {

    private static final Long ID = 1l;
    private static final Plan.Goal GOAL = Plan.Goal.MUSCLES;
    private static final String NAME = "name";
    private static final Long ACCOUNT_ID = 2l;
    private static PlanDto PLAN_FLOW = new PlanDto();
    private static Plan PLAN = new Plan();

    @InjectMocks
    private static PlanConverter CONVERTER;

    @Mock
    private PlanManager planManager;


    @Before
    public void setUp() {
        PLAN_FLOW.setId(ID);
        PLAN_FLOW.setGoal(GOAL);
        PLAN_FLOW.setName(NAME);
        PLAN_FLOW.setCreatorId(ACCOUNT_ID);

        PLAN.setId(ID);
        PLAN.setGoal(GOAL);
        PLAN.setName(NAME);
        PLAN.setCreator(new Account(ACCOUNT_ID));

        Mockito.when(planManager.retrieve(ID, false)).thenReturn(PLAN_FLOW);
    }

    @Test
    public void testToFlowObject() throws Exception {
        PlanDto flow = CONVERTER.fromEntity(PLAN, false);
        assertEquals(flow, PLAN_FLOW);
    }

    @Test
    public void testFromFlowObject() throws Exception {
        Plan plan = CONVERTER.toEntity(PLAN_FLOW);
        assertEquals(plan, PLAN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToFlowObjectNull() throws Exception {
        CONVERTER.fromEntity(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromFlowObjectNull() throws Exception {
        CONVERTER.toEntityList(null);
    }
}