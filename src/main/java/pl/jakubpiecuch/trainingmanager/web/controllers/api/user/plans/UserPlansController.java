package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.plans;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(method = { RequestMethod.POST })
    public void create(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody UserPlan userPlan) {
        versionServices.get(version).startPlan(userPlan);
    }
}
