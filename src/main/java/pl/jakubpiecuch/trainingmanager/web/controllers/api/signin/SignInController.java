package pl.jakubpiecuch.trainingmanager.web.controllers.api.signin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/{version}/signin")
@RestController
public class SignInController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity signIn(@PathVariable ApiVersionService.Version version, @RequestBody Authentication authentication) {
        versionServices.get(version).signIn(authentication);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = { RequestMethod.GET })
    public Authentication signed(@PathVariable ApiVersionService.Version version) {
        return versionServices.get(version).signed();
    }
}