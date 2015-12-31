package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.plans;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlan;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2015-01-22.
 */
@RequestMapping(ApiURI.API_USERS_PLANS_PATH)
@RestController
public class UserPlansController extends AbstractController {

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.PLAN_STARTER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = {RequestMethod.POST})
    public void create(@PathVariable ApiVersionService.Version version, @RequestBody UserPlan plan) {
        versionServices.get(version).starter().start(plan.getPlanId(), plan.getYear(), plan.getWeek());
    }
}