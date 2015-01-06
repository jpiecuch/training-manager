package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.WorkoutDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Workout;

import java.util.List;

public class WorkoutDaoImpl extends CoreDaoImpl implements WorkoutDao {

    @Override
    public Workout findById(long id) {
        return (Workout) session().createQuery("SELECT w FROM Workout w WHERE w.id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<Workout> findByParentId(long phaseId) {
        return session().createQuery("SELECT w FROM Workout w WHERE w.phase.id = :phaseId").setParameter("phaseId", phaseId).list();
    }
}