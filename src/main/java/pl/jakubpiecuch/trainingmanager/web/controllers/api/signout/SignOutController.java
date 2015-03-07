package pl.jakubpiecuch.trainingmanager.web.controllers.api.signout;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

@RequestMapping("api/{version}/signout")
@RestController
public class SignOutController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity signOut(@PathVariable ApiVersionService.Version version) {
        versionServices.get(version).signOut();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
