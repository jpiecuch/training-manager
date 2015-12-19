package pl.jakubpiecuch.trainingmanager.web.controllers.api.signon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/{version}/signon")
@RestController
public class SignOnController extends AbstractController {

    @PreAuthorize(value = Permissions.IS_ANONYMOUS)
    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity signOn(@PathVariable ApiVersionService.Version version, HttpServletRequest request, @RequestBody Registration registration) {
        versionServices.get(version).signOn(registration, request.getLocale());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
