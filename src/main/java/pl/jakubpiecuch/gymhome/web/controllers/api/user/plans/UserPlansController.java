package pl.jakubpiecuch.gymhome.web.controllers.api.user.plans;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.user.plan.UserPlan;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractController;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;

/**
 * Created by Rico on 2015-01-22.
 */
@RequestMapping(ApiURI.API_USERS_PLANS_PATH)
@RestController
public class UserPlansController extends AbstractController {

    @RequestMapping(method = {RequestMethod.POST})
    public void create(@PathVariable ApiVersionService.Version version, @RequestBody UserPlan plan) {
        versionServices.get(version).starter().start(plan.getPlanId(), plan.getYear(), plan.getWeek());
    }
}