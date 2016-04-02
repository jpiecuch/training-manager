package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.password;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

@RequestMapping(ApiURI.API_RESET_PASSWORD)
@RestController
public class ResetPasswordController extends AbstractController {

    @RequestMapping(method = {RequestMethod.POST})
    public void reset(@PathVariable ApiVersionService.Version version, @RequestBody String email, Locale locale) {
        versionServices.get(version).manageUser().password(email, locale);
    }
}
