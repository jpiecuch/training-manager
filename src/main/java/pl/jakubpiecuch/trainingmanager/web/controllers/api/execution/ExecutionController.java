package pl.jakubpiecuch.trainingmanager.web.controllers.api.execution;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.execution.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.service.execution.session.SessionExecutionCriteria;
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

    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<ExecutionDto> executions(@PathVariable ApiVersionService.Version version,
                               @RequestParam(value = "from", required = false) Date from,
                               @RequestParam(value = "to", required = false) Date to,
                               @RequestParam(value = "excludedId", required = false) Long[] excludedIds,
                               Locale locale) throws Exception {
        return versionServices.get(version).executions(new SessionExecutionCriteria(locale.getLanguage()).addDateRangeRestriction(from, to).addExcludedIdRestriction(excludedIds));
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public ExecutionDto execution(@PathVariable ApiVersionService.Version version, @PathVariable long id, Locale locale) {
        return versionServices.get(version).executions(new SessionExecutionCriteria(locale.getLanguage()).setIdRestriction(id)).getResult().get(0);
    }
}
