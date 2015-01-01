package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.PhaseDao;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

import java.util.List;

import static org.junit.Assert.*;

public class PhaseDaoImplTest extends BaseDAOTestCase {

    private static final Long PLAN_ID = 1l;
    private static final long PHASE_COUNT = 1l;
    private static final Plan.Goal GOAL = Plan.Goal.strength;
    private static final Integer POSITION = 1;
    private static final Integer WEEKS = 3;

    @Autowired
    private PhaseDao phaseDao;

    @Test
    public void testFindByPlanId() throws Exception {
        List<Phase> list = phaseDao.findByParentId(PLAN_ID);
        assertEquals(PHASE_COUNT, list.size());

        Phase phase = list.get(0);

        assertEquals(GOAL, phase.getGoal());
        assertEquals(POSITION, phase.getPosition());
        assertEquals(WEEKS, phase.getWeeks());
        assertEquals(PLAN_ID, phase.getPlan().getId());
    }
}