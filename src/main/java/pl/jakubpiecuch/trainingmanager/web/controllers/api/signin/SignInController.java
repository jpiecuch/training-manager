package pl.jakubpiecuch.trainingmanager.web.controllers.api.signin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/{version}/signin")
@Controller
public class SignInController extends AbstractController {
    
    @RequestMapping(method = { RequestMethod.POST })
    public @ResponseBody
    ResponseEntity signIn(@PathVariable ApiVersionService.Version version, @RequestBody Authentication authentication) throws Exception {
        versionServices.get(version).signIn(authentication);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = { RequestMethod.GET })
    public @ResponseBody
    Authentication signed(@PathVariable ApiVersionService.Version version, HttpServletResponse response) throws Exception {
        return versionServices.get(version).signed();
    }
}