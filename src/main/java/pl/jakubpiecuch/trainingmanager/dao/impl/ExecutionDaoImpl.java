package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import java.util.List;

public class ExecutionDaoImpl extends AbstractRepoDao<Execution, UserWorkoutCriteria> implements ExecutionDao {

    @Override
    public List<Execution> findByParentId(long parentId) {
        return session().createQuery("SELECT e FROM Execution e LEFT JOIN FETCH e.exercise ex LEFT JOIN FETCH ex.description d WHERE e.workout.id = :workoutId").setParameter("workoutId", parentId).list();
    }
}