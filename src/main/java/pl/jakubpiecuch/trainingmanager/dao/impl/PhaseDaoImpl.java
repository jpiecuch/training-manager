package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.PhaseDao;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

import java.util.List;

public class PhaseDaoImpl extends CoreDaoImpl implements PhaseDao {

    @Override
    public List<Phase> findPhaseByPlanId(long planId) {
        return session().createQuery("SELECT p FROM Phase p WHERE p.plan.id = :planId").setParameter("planId", planId).list();
    }
}