package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.workouts;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractRepositoryController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-22.
 */
@RequestMapping(ApiURI.API_USERS_WORKOUTS_PATH)
@RestController
public class UserWorkoutsController extends AbstractRepositoryController<UserWorkoutDto, UserWorkoutCriteria> {

    @Override
    protected UserWorkoutCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new UserWorkoutCriteria(locale.getLanguage())
                .addDateRangeRestriction(resolveDate(parameters.getFirst("from")), resolveDate(parameters.getFirst("to")))
                .addStateRestrictions(resolveEnumValues(parameters.get("state"), UserWorkout.State.class));
    }
}
