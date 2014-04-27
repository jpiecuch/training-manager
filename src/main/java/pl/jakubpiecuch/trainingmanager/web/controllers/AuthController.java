package pl.jakubpiecuch.trainingmanager.web.controllers;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import pl.jakubpiecuch.trainingmanager.web.services.AuthenticatedUserUtil;
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
    public @ResponseBody boolean create(@RequestBody @Valid Users user, Errors errors, Locale locale) {
        if (!errors.hasErrors()) {
            return localAuthenticationService.create(user, locale);
        }
        return false;
    }
    
    @RequestMapping(value = { "availability/{field}"}, method = {RequestMethod.GET})
    public @ResponseBody boolean fieldAvailability(@PathVariable String field, @RequestParam String value) {
        return localAuthenticationService.availability(field, value);
    }
    
    @RequestMapping(value = "activate/{code}", method = RequestMethod.GET)
    public String login(@PathVariable String code, HttpServletRequest request) {
        request.getSession().setAttribute("user.activated", localAuthenticationService.activate(code));
        return "redirect:/login.html";
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
