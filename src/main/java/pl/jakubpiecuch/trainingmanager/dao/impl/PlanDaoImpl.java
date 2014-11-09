package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Bars;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

public class PlanDaoImpl extends CoreDaoImpl implements PlanDao {

    @Override
    public Plan getById(long id) {
        return (Plan) session().createQuery("SELECT p FROM Plan p LEFT JOIN FETCH p.exercises pe LEFT JOIN FETCH pe.exercise LEFT JOIN FETCH p.creator WHERE p.id = :id").setParameter("id", id).uniqueResult();
    }
}