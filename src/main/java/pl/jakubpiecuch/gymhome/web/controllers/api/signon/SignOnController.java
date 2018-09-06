package pl.jakubpiecuch.gymhome.web.controllers.api.signon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.user.model.Registration;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/{version}/signon")
@RestController
public class SignOnController extends AbstractController {

    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity signOn(@PathVariable ApiVersionService.Version version, HttpServletRequest request, @RequestBody Registration registration) {
        versionServices.get(version).signOn(registration.getProvider()).signOn(registration, request.getLocale());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
