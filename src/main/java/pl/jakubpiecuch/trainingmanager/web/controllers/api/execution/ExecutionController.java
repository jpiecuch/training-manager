package pl.jakubpiecuch.trainingmanager.web.controllers.api.execution;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_EXECUTION_PATH)
@RestController
public class ExecutionController extends AbstractController {

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.PUT })
    public void create(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody ExecutionDto execution) {
        versionServices.get(version).updateExecution(execution);
    }
}
