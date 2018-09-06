package pl.jakubpiecuch.gymhome.impl;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.gymhome.BaseUnitDaoTestCase;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.Phase;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanCriteria;

import java.util.List;

import static org.junit.Assert.*;

public class PlanDaoImplTest extends BaseUnitDaoTestCase<Plan> {

    private static final Long ID = 1l;
    private static final Plan.Goal GOAL = Plan.Goal.MUSCLES;
    private static final String NAME = "Main plan";
    private static final Long NOT_PERSISTED_ID = 99l;
    private static final String NAME_SAVE = "save plan";
    private static final Plan.Goal GOAL_SAVE = Plan.Goal.STRENGTH;
    private static final Long ACCOUNT_ID = 1l;
    private static final Account CREATOR_SAVE = new Account(ACCOUNT_ID);

    private static Plan PLAN = new Plan();
    private static Phase PHASE = new Phase();

    @Autowired
    private RepoDao<Plan, PlanCriteria> planDao;

    @Before
    public void setUp() {
        PLAN.setName(NAME_SAVE);
        PLAN.setGoal(GOAL_SAVE);
        PLAN.setCreator(CREATOR_SAVE);

        PHASE.setDescription("phase");
        PHASE.setPosition(1);
        PHASE.setGoal(GOAL);
        PHASE.setWeeks(2);
        PHASE.setPlan(PLAN);

        PLAN.getPhases().add(PHASE);
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

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList("name", "goal", "creator");
    }

    @Override
    protected CoreDao getDao() {
        return planDao;
    }

    @Override
    protected Plan getEntity() {
        return new Plan();
    }
}