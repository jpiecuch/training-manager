package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.workouts;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Rico on 2015-01-22.
 */
@RequestMapping(ApiURI.API_USERS_WORKOUTS_PATH)
@RestController
public class UserWorkoutsController extends AbstractController {

    @RequestMapping(method = { RequestMethod.GET })
    public PageResult create(@PathVariable ApiVersionService.Version version, @PathVariable long id,
                       @RequestParam(value = "from", required = false) Date from,
                       @RequestParam(value = "to", required = false) Date to, Locale locale) {
        return versionServices.get(version).retrieveFromRepository(new UserWorkoutCriteria(locale.getLanguage()).addDateRangeRestriction(from, to), Repositories.WORKOUT);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public UserWorkoutDto execution(@PathVariable ApiVersionService.Version version, @PathVariable long id, Locale locale) {
        return (UserWorkoutDto) versionServices.get(version).retrieveFromRepository(new UserWorkoutCriteria(locale.getLanguage()).setIdRestriction(id), Repositories.WORKOUT).getResult().get(0);
    }
}
