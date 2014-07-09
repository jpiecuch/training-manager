package pl.jakubpiecuch.trainingmanager.web.controllers;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
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
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.domain.Users;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.web.validator.UserValidator;

@Controller
@RequestMapping(value = "/authentication")
public class AuthenticationController {
    
    protected final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    
    private AuthenticationService localAuthenticationService;
    private UserValidator userValidator;
    
    @InitBinder(value = "user")
    protected void userInitBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }
    
    @InitBinder(value = "userPasswords")
    protected void passwordsInitBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }
    
    @RequestMapping(value = "reset", method = { RequestMethod.POST })
    public @ResponseBody AuthenticationService.ResetStatus reset(@RequestParam String email) {
        return localAuthenticationService.resetPassword(email);
    }
    
    @RequestMapping(value = "password/change/{code}", method = { RequestMethod.GET })
    public String changePassword(@PathVariable String code) {
        return "changepassword";
    }
    
    @RequestMapping(value = "password/change/{code}", method = { RequestMethod.POST })
    public @ResponseBody boolean changePasswordSubmit(@PathVariable String code, @RequestBody @Valid Users userPasswords, Errors errors) {
        return false;
    }
    
    @RequestMapping(value = "create", method = { RequestMethod.POST })
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
    
    @RequestMapping(value = "socialsignup", method = RequestMethod.GET)
    public String socialSignUp(WebRequest request) {
        localAuthenticationService.socialSignUp(request);
        return "redirect:/";
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
