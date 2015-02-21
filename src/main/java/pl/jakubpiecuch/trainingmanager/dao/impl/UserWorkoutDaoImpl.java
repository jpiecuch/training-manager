package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import java.util.List;

/**
 * Created by Rico on 2015-01-25.
 */
public class UserWorkoutDaoImpl extends AbstractRepoDao<UserWorkout, UserWorkoutCriteria> implements UserWorkoutDao {

    @Override
    public List<UserWorkout> findByParentId(long parentId) {
        return session().createQuery("SELECT u FROM UserWorkout u WHERE u.workout.id = :workoutId").setParameter("workoutId", parentId).list();
    }
}
