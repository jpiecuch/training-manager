package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.BaseUnitDaoTestCase;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Execution;

import java.util.List;

import static org.junit.Assert.*;

public class ExecutionDaoImplTest extends BaseUnitDaoTestCase<Execution> {

    public static final Long BILATERAL_EXECUTION = 1l;
    public static final Long UNILATERAL_EXECUTION = 2l;

    public static final List<Integer> SETS = Lists.newArrayList(15, 15, 15);
    public static final List<Integer> LEFT_SETS = Lists.newArrayList(12, 10, 8, 8, 6);
    public static final List<Integer> RIGHT_SETS = Lists.newArrayList(12, 10, 8, 8, 6);
    private static final List<Double> WEIGHTS = Lists.newArrayList(80.0, 80.0, 80.0);
    private static final List<Double> LEFT_WEIGHTS = Lists.newArrayList(17.0, 18.0, 19.25, 20.25, 21.25);
    private static final List<Double> RIGHT_WEIGHTS = Lists.newArrayList(17.0, 18.0, 19.25, 20.25, 21.25);

    @Autowired
    private CoreDao<Execution> executionDao;

    @Test
    public void testFindById() throws Exception {
        Execution bilateralExecution = executionDao.findById(BILATERAL_EXECUTION);

        assertNotNull(bilateralExecution);
        assertEquals(1, bilateralExecution.getResults().size());
        Execution.Result result = bilateralExecution.getResults().get(0);

        assertNull(result.getSide());
        assertEquals(SETS, result.getSets());
        assertEquals(WEIGHTS, result.getWeights());
        Execution unilateralExecution = executionDao.findById(UNILATERAL_EXECUTION);

        assertNotNull(unilateralExecution);
        assertEquals(2, unilateralExecution.getResults().size());

        Execution.Result leftResult = unilateralExecution.getResults().get(0);

        assertEquals(Execution.Result.LEFT_SIDE_CODE, leftResult.getSide());
        assertEquals(LEFT_SETS, leftResult.getSets());
        assertEquals(LEFT_WEIGHTS, leftResult.getWeights());

        Execution.Result rightResult = unilateralExecution.getResults().get(1);

        assertEquals(Execution.Result.RIGHT_SIDE_CODE, rightResult.getSide());
        assertEquals(RIGHT_SETS, rightResult.getSets());
        assertEquals(RIGHT_WEIGHTS, rightResult.getWeights());


    }

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList("exercise", "workout", "state");
    }

    @Override
    protected CoreDao getDao() {
        return executionDao;
    }

    @Override
    protected Execution getEntity() {
        return new Execution();
    }
}