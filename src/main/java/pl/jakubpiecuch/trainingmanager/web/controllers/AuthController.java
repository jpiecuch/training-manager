package pl.jakubpiecuch.trainingmanager.web.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.domain.Users;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.web.validator.UserValidator;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    
    private AuthenticationService localAuthenticationService;
    private UserValidator userValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }
    
    @RequestMapping(value = "reset", method = {RequestMethod.POST})
    public @ResponseBody AuthenticationService.ResetStatus reset(@RequestParam String email) {
        return localAuthenticationService.resetPassword(email);
    }
    
    @RequestMapping(value = "create", method = {RequestMethod.POST})
    public @ResponseBody boolean create(@RequestBody @Valid Users user, Errors errors) {
        if (!errors.hasErrors()) {
            return localAuthenticationService.create(user);
        }
        return false;
    }
    
    @RequestMapping(value = { "availability/{field}"}, method = {RequestMethod.GET})
    public @ResponseBody boolean fieldAvailability(@PathVariable String field, @RequestParam String value) {
        return localAuthenticationService.availability(field, value);
    }

    @Autowired
    public void setLocalAuthenticationService(AuthenticationService localAuthenticationService) {
        this.localAuthenticationService = localAuthenticationService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
}
