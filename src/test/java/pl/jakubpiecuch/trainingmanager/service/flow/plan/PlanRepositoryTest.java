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
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class PlanRepositoryTest {

    private static final long ID = 1l;
    private static final long NOT_PERSIST_ID = 99l;
    private static final long USED_ID = 44l;
    private static PlanDto PLAN_FLOW = new PlanDto();
    private static Plan PLAN = new Plan();
    private static Plan USED_PLAN = new Plan();

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

        USED_PLAN.setId(USED_ID);
        USED_PLAN.setUsed(true);


        Mockito.when(dao.findById(ID)).thenReturn(PLAN);
        Mockito.when(dao.findById(USED_ID)).thenReturn(USED_PLAN);
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

    @Test
    public void testDelete() {
        planRepository.delete(ID);

        Mockito.verify(dao, Mockito.times(1)).findById(ID);
        Mockito.verify(dao, Mockito.times(1)).delete(PLAN);
    }

    @Test
    public void testDeleteNotExists() {
        try {
            planRepository.delete(NOT_PERSIST_ID);
            fail();
        } catch (NotFoundException ex) {
        }
        Mockito.verify(dao, Mockito.times(1)).findById(NOT_PERSIST_ID);
        Mockito.verify(dao, Mockito.never()).delete(Mockito.any(Plan.class));

    }

    @Test
    public void testDeleteUsed() {
        try {
            planRepository.delete(USED_ID);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        Mockito.verify(dao, Mockito.times(1)).findById(USED_ID);
        Mockito.verify(dao, Mockito.never()).delete(Mockito.any(Plan.class));
    }

}