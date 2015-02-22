package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;

import java.util.List;

public class PlanDaoImpl extends AbstractRepoDao<Plan, PlanCriteria> implements PlanDao {
    @Override
    public List<Plan> findByParentId(long parentId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}