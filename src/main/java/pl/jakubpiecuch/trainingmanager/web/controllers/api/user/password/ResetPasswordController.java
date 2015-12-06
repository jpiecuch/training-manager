package pl.jakubpiecuch.trainingmanager.web.controllers.api.user.password;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

@RequestMapping(ApiURI.API_RESET_PASSWORD)
@RestController
@PreAuthorize(value = Permissions.IS_ANONYMOUS)
public class ResetPasswordController extends AbstractController {

    @PreAuthorize(value = Permissions.IS_ANONYMOUS)
    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity reset(@PathVariable ApiVersionService.Version version, @RequestBody String email, Locale locale) {
        versionServices.get(version).resetPassword(email, locale);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
