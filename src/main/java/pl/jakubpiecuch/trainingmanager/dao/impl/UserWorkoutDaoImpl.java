package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import java.util.List;

/**
 * Created by Rico on 2015-01-25.
 */
public class UserWorkoutDaoImpl extends CoreDaoImpl implements UserWorkoutDao, RepoDao<UserWorkout, UserWorkoutCriteria> {

    @Override
    public UserWorkout findById(long id) {
        return (UserWorkout) session().createQuery("SELECT u FROM UserWorkout u WHERE u.id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<UserWorkout> findByParentId(long parentId) {
        return session().createQuery("SELECT u FROM UserWorkout u WHERE u.workout.id = :workoutId").setParameter("workoutId", parentId).list();
    }

    @Override
    public PageResult<UserWorkout> findByCriteria(UserWorkoutCriteria criteria) {
        final List<Object[]> result = criteria.query(session()).list();

        return new PageResult<UserWorkout>() {
            @Override
            public List<UserWorkout> getResult() {
                return Lists.transform(result, new Function<Object[], UserWorkout>() {
                    @Override
                    public UserWorkout apply(Object[] in) {
                        return (UserWorkout) in[0];
                    }
                });
            }

            @Override
            public long getCount() {
                return result.size() > 0 ? (Long)result.get(0)[1] : 0;
            }
        };
    }
}
