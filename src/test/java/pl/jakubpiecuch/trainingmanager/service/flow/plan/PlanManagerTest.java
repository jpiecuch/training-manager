package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.jakubpiecuch.trainingmanager.dao.BaseDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowConverter;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PlanManagerTest {

    private static final long ID = 1l;
    private static final long NOT_PERSIST_ID = 99l;
    private static PlanDto PLAN_FLOW = new PlanDto();
    private static Plan PLAN = new Plan();
    @InjectMocks
    private PlanManager planManager;

    @Mock
    BaseDao<Plan> dao;

    @Mock
    FlowConverter<PlanDto, Plan> converter;

    @Before
    public void setUp() {
        PLAN.setId(ID);

        PLAN_FLOW.setId(ID);

        Mockito.when(dao.findById(ID)).thenReturn(PLAN);
        Mockito.when(dao.findById(NOT_PERSIST_ID)).thenReturn(null);
        Mockito.when(converter.toFlowObject(PLAN, false)).thenReturn(PLAN_FLOW);
        Mockito.when(converter.toFlowObject(null, false)).thenThrow(IllegalArgumentException.class);
    }

    @Test
    public void testGetElement() {
        PlanDto flow = planManager.retrieve(ID, false);
        assertEquals(flow, PLAN_FLOW);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementNotPersist() {
        planManager.retrieve(NOT_PERSIST_ID, false);
    }

}