package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.WorkoutDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Workout;

import java.util.List;

public class WorkoutDaoImpl extends CoreDaoImpl<Workout> implements WorkoutDao {

    @Override
    public List<Workout> findByParentId(long phaseId) {
        return session().createQuery("SELECT w FROM Workout w WHERE w.phase.id = :phaseId ORDER BY w.position").setParameter("phaseId", phaseId).list();
    }
}