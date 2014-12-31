package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.ExerciseDao;
import pl.jakubpiecuch.trainingmanager.dao.WorkoutDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

import java.util.List;

public class ExerciseDaoImpl extends CoreDaoImpl implements ExerciseDao {

    @Override
    public List<Exercise> findByWorkoutId(long workoutId) {
        return session().createQuery("SELECT e FROM Exercise e WHERE e.workout.id = :workoutId").setParameter("workoutId", workoutId).list();
    }
}