package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.plan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.AbstractFlowController;

import java.util.Locale;

/**
 * Created by Rico on 2014-12-31.
 */
@RequestMapping(ApiURI.API_PLAN_PATH)
@RestController
public class PlanController extends AbstractFlowController {

    @Override
    protected Flow.Hierarchy getHierarchy() {
        return Flow.Hierarchy.PLAN;
    }

    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity create(@PathVariable ApiVersionService.Version version, @RequestBody PlanDto flow) throws Exception {
        return super.create(version, flow);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.PUT })
    public void update(@PathVariable ApiVersionService.Version version, @RequestBody PlanDto flow, @PathVariable(ApiURI.ID_PARAM) Long id) throws Exception {
        super.update(version, flow);
    }

    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<PlanDto> plans(@PathVariable ApiVersionService.Version version,
                                                @RequestParam(value = "goal", required = false) Plan.Goal[] goals,
                                                @RequestParam(value = "firstResult", required = false, defaultValue = "0") Integer firstResult,
                                                @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults,
                                                Locale locale) throws Exception {
        return versionServices.get(version).retrieveFromRepository(new PlanCriteria(locale.getLanguage()).setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults).addGoalRestrictions(goals), Repositories.PLAN);
    }
}
