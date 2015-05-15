package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlanRepositoryTest {

    private static final long ID = 1l;
    private static final long NOT_PERSIST_ID = 99l;
    private static PlanDto PLAN_FLOW = new PlanDto();
    private static Plan PLAN = new Plan();
    @InjectMocks
    private PlanRepository planRepository;

    @Mock
    PlanDao dao;

    @Mock
    PlanConverter converter;

    @Before
    public void setUp() {
        PLAN.setId(ID);

        PLAN_FLOW.setId(ID);

        Mockito.when(dao.findById(ID)).thenReturn(PLAN);
        Mockito.when(dao.findById(NOT_PERSIST_ID)).thenReturn(null);
        Mockito.when(converter.fromEntity(PLAN)).thenReturn(PLAN_FLOW);
        Mockito.when(converter.fromEntity(null)).thenThrow(IllegalArgumentException.class);
    }

    @Test
    public void testGetElement() {
        PlanDto flow = planRepository.retrieve(ID);
        assertEquals(flow, PLAN_FLOW);
    }

    @Test(expected = NotFoundException.class)
    public void testGetElementNotPersist() {
        planRepository.retrieve(NOT_PERSIST_ID);
    }

}