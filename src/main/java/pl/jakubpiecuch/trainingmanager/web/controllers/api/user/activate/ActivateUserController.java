package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.activate;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

@RequestMapping(ApiURI.API_ACTIVATE_USER)
@RestController
public class ActivateUserController extends AbstractController {

    @RequestMapping(method = {RequestMethod.POST})
    public void activate(@PathVariable ApiVersionService.Version version, @RequestBody String code) {
        versionServices.get(version).manageUser().activate(code);
    }
}
