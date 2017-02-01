package pl.jakubpiecuch.trainingmanager.web.controllers.api.execution;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.RepositoryType;
import pl.jakubpiecuch.trainingmanager.service.repository.execution.ExecutionCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractReadRepositoryController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_EXECUTION_PATH)
@RestController
public class ExecutionController extends AbstractReadRepositoryController<Execution, ExecutionCriteria> {

    @Override
    protected ExecutionCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new ExecutionCriteria(locale.getLanguage());
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = {RequestMethod.PUT})
    public void update(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody ExecutionDto execution) {
        execution.setId(id);
        versionServices.get(version).update(RepositoryType.EXECUTION).update(execution);
    }
}
