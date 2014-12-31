package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.WorkoutDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Workout;

import java.util.List;

public class WorkoutDaoImpl extends CoreDaoImpl implements WorkoutDao {

    @Override
    public List<Workout> findByPhaseId(long phaseId) {
        return session().createQuery("SELECT w FROM Workout w WHERE w.phase.id = :phaseId").setParameter("phaseId", phaseId).list();
    }
}