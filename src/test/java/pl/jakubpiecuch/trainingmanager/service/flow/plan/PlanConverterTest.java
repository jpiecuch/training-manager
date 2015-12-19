package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlanConverterTest {

    private static final Long ID = 1l;
    private static final Plan.Goal GOAL = Plan.Goal.MUSCLES;
    private static final String NAME = "name";
    private static final Long ACCOUNT_ID = 2l;
    private static final Boolean USED = false;
    private static final String CONFIG = "{\"firstName\":\"first\",\"lastName\":\"last\"}";
    private static PlanDto PLAN_FLOW = new PlanDto();
    private static Plan PLAN = new Plan();
    @InjectMocks
    private static PlanConverter CONVERTER;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    PhaseConverter phaseConverter;

    @Mock
    RepoDao<Plan, PlanCriteria> planDao;


    @Before
    public void setUp() throws Exception {
        Account account = new Account(ACCOUNT_ID);
        account.setConfig(CONFIG);
        PLAN_FLOW.setId(ID);
        PLAN_FLOW.setGoal(GOAL);
        PLAN_FLOW.setName(NAME);
        PLAN_FLOW.setCreatorId(ACCOUNT_ID);
        PLAN_FLOW.setUsed(USED);
        PLAN_FLOW.setEditable(true);
        PLAN_FLOW.setPhases(new ArrayList<PhaseDto>());

        PLAN.setId(ID);
        PLAN.setGoal(GOAL);
        PLAN.setName(NAME);
        PLAN.setCreator(account);
        PLAN.setUsed(USED);
        PLAN.setPhases(new ArrayList<Phase>());

        Mockito.when(authenticationService.signed()).thenReturn(new Authentication(account));
        Mockito.when(planDao.findById(ID)).thenReturn(PLAN);
    }

    @Test
    public void testToFlowObject() throws Exception {
        PlanDto flow = CONVERTER.fromEntity(PLAN);
        assertEquals(flow, PLAN_FLOW);
    }

    @Test
    public void testFromFlowObject() throws Exception {
        Plan plan = CONVERTER.toEntity(PLAN_FLOW, PLAN);
        assertEquals(PLAN, plan);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToFlowObjectNull() throws Exception {
        CONVERTER.fromEntity(null);
    }
}