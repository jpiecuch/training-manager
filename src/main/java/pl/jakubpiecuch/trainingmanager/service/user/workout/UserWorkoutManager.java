package pl.jakubpiecuch.trainingmanager.service.user.workout;

import pl.jakubpiecuch.trainingmanager.service.repository.Repository;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

/**
 * Created by Rico on 2015-01-18.
 */
public interface UserWorkoutManager extends Repository<UserWorkoutDto, UserWorkoutCriteria> {
}
