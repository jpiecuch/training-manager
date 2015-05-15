package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;

public interface PlanDao extends RepoDao<Plan, PlanCriteria> {
}
