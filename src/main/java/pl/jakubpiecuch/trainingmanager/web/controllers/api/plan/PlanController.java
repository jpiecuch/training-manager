package pl.jakubpiecuch.trainingmanager.web.controllers.api.plan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2014-12-31.
 */
@RequestMapping(ApiURI.API_PLAN_PATH)
@RestController
public class PlanController extends AbstractController {

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.PLAN_CREATOR + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity create(@PathVariable ApiVersionService.Version version, @RequestBody PlanDto flow) {
        return new ResponseEntity(versionServices.get(version).createFlow(Flow.Hierarchy.PLAN, flow), HttpStatus.CREATED);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.PLAN_UPDATER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.PUT })
    public void update(@PathVariable ApiVersionService.Version version, @RequestBody PlanDto flow, @PathVariable(ApiURI.ID_PARAM) Long id) {
        flow.setId(id);
        versionServices.get(version).updateFlow(Flow.Hierarchy.PLAN, flow);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.PLAN_VIEWER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<PlanDto> plans(@PathVariable ApiVersionService.Version version,
                                                @RequestParam(value = "goal", required = false) Plan.Goal[] goals,
                                                @RequestParam(value = "firstResult", required = false, defaultValue = "0") Integer firstResult,
                                                @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults,
                                                Locale locale) {
        return versionServices.get(version).retrieveFromRepository(new PlanCriteria(locale.getLanguage()).setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults).addGoalRestrictions(goals), Repositories.PLAN);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.PLAN_VIEWER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public ResponseEntity plan(@PathVariable ApiVersionService.Version version, @PathVariable Long id, @RequestParam(required = false, defaultValue = "false") boolean full) {
        return new ResponseEntity(versionServices.get(version).flow(Flow.Hierarchy.PLAN, id, full), HttpStatus.OK);
    }
}