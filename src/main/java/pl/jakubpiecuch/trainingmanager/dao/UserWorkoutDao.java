package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;

import java.util.List;

/**
 * Created by Rico on 2015-01-25.
 */
public interface UserWorkoutDao extends CoreDao<UserWorkout> {
    List<UserWorkout> findByParentId(long parentId);
}
