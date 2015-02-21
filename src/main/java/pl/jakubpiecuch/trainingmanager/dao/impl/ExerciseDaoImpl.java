package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.ExerciseDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

import java.util.List;

public class ExerciseDaoImpl extends CoreDaoImpl<Exercise> implements ExerciseDao {

    @Override
    public List<Exercise> findByParentId(long parentId) {
        return session().createQuery("SELECT e FROM Exercise e LEFT JOIN FETCH e.description WHERE e.workout.id = :workoutId ORDER BY e.position ASC").setParameter("workoutId", parentId).list();
    }
}