package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.activate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

@RequestMapping(ApiURI.API_ACTIVATE_USER)
@RestController
public class ActivateUserController extends AbstractController {

    @PreAuthorize(value = Permissions.IS_ANONYMOUS)
    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity activate(@PathVariable ApiVersionService.Version version, @RequestBody String code) {
        versionServices.get(version).activate(code);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
