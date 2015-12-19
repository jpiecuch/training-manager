package pl.jakubpiecuch.trainingmanager.web.controllers.api.execution;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_EXECUTION_PATH)
@RestController
public class ExecutionController extends AbstractController {

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.EXECUTION_CREATOR + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = {RequestMethod.PUT})
    public void create(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody ExecutionDto execution) {
        execution.setId(id);
        versionServices.get(version).updateExecution(execution);
    }
}
