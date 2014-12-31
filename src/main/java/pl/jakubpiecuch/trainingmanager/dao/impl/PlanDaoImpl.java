package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

public class PlanDaoImpl extends CoreDaoImpl implements PlanDao {


    @Override
    public Plan findById(long id) {
        return (Plan) session().createQuery("SELECT p FROM Plan p WHERE p.id = :id").setParameter("id", id).uniqueResult();
    }
}