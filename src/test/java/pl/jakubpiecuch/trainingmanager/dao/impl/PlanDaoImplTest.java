package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

import static org.junit.Assert.*;

public class PlanDaoImplTest extends BaseDAOTestCase {

    private static final Long ID = 1l;
    private static final Plan.Goal GOAL = Plan.Goal.MUSCLES;
    private static final String NAME = "Main plan";
    private static final Long NOT_PERSISTED_ID = 99l;
    private static final String NAME_SAVE = "save plan";
    private static final Plan.Goal GOAL_SAVE = Plan.Goal.STRENGTH;
    private static final Long ACCOUNT_ID = 1l;
    private static final Account CREATOR_SAVE = new Account(ACCOUNT_ID);

    private static Plan PLAN = new Plan();

    @Autowired
    private PlanDao planDao;

    @Before
    public void setUp() {
        PLAN.setName(NAME_SAVE);
        PLAN.setGoal(GOAL_SAVE);
        PLAN.setCreator(CREATOR_SAVE);
    }

    @Test
    public void testGetById() throws Exception {
        Plan plan = planDao.findById(ID);
        assertEquals(ID, plan.getId());
        assertEquals(GOAL, plan.getGoal());
        assertEquals(NAME, plan.getName());

        assertNull(planDao.findById(NOT_PERSISTED_ID));
    }

    @Test
    public void testSave() {
        planDao.create(PLAN);
        planDao.flush();
        assertNotNull(PLAN.getId());
    }
}