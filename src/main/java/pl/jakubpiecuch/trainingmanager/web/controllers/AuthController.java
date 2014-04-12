package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.domain.Users;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    
    private AuthenticationService localAuthenticationService;
    
    @RequestMapping(value = "reset", method = {RequestMethod.POST})
    public @ResponseBody AuthenticationService.ResetStatus reset(@RequestParam String email) {
        return localAuthenticationService.resetPassword(email);
    }
    
    @RequestMapping(value = "create", method = {RequestMethod.POST})
    public @ResponseBody boolean create(@RequestBody Users user) {
        return localAuthenticationService.create(user);
    }

    @Autowired
    public void setLocalAuthenticationService(AuthenticationService localAuthenticationService) {
        this.localAuthenticationService = localAuthenticationService;
    }
}
