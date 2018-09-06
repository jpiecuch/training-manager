package pl.jakubpiecuch.gymhome.web.controllers.api.user.activate;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractController;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;

@RequestMapping(ApiURI.API_ACTIVATE_USER)
@RestController
public class ActivateUserController extends AbstractController {

    @RequestMapping(method = {RequestMethod.POST})
    public void activate(@PathVariable ApiVersionService.Version version, @RequestBody String code) {
        versionServices.get(version).manageUser().activate(code);
    }
}
