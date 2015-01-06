package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.ExerciseDao;
import pl.jakubpiecuch.trainingmanager.dao.WorkoutDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

import java.util.List;

public class ExerciseDaoImpl extends CoreDaoImpl implements ExerciseDao {

    @Override
    public Exercise findById(long id) {
        return (Exercise) session().createQuery("SELECT e FROM Exercise e WHERE e.id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<Exercise> findByParentId(long parentId) {
        return session().createQuery("SELECT e FROM Exercise e LEFT JOIN FETCH e.description WHERE e.workout.id = :workoutId").setParameter("workoutId", parentId).list();
    }
}