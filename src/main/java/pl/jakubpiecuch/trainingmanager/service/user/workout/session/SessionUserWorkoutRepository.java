package pl.jakubpiecuch.trainingmanager.service.user.workout.session;

import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.repository.AbstractConversionRepository;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;

/**
 * Created by Rico on 2015-01-18.
 */
public class SessionUserWorkoutRepository extends AbstractConversionRepository<UserWorkoutDto, UserWorkout, UserWorkoutCriteria> {

    @Override
    public long create(UserWorkoutDto element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserWorkout getEmpty() {
        return new UserWorkout();
    }

}
