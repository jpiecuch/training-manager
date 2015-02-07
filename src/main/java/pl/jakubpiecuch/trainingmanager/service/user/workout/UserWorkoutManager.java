package pl.jakubpiecuch.trainingmanager.service.user.workout;

import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;

/**
 * Created by Rico on 2015-01-18.
 */
public interface UserWorkoutManager extends ReadRepository<UserWorkoutDto, UserWorkoutCriteria> {
}