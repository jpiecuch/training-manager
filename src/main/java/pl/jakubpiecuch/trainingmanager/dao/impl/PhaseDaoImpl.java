package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.PhaseDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Phase;

import java.util.List;

public class PhaseDaoImpl extends CoreDaoImpl<Phase> implements PhaseDao {

    @Override
    public List<Phase> findByParentId(long planId) {
        return session().createQuery("SELECT p FROM Phase p WHERE p.plan.id = :planId ORDER BY p.position").setParameter("planId", planId).list();
    }
}