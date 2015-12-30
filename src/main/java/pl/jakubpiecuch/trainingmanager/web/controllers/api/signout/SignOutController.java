package pl.jakubpiecuch.trainingmanager.web.controllers.api.signout;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/{version}/signout")
@RestController
public class SignOutController extends AbstractController {

    @PreAuthorize(value = Permissions.IS_AUTHENTICATED)
    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity signOut(@PathVariable ApiVersionService.Version version, HttpServletRequest request, HttpServletResponse response) {
        versionServices.get(version).authentication().signOut(request, response);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
