package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/{version}/authentication")
public class ApiAuthenticationController {
    
    /*protected final static Logger LOGGER = LoggerFactory.getLogger(ApiAuthenticationController.class);
    
    private AuthenticationService localAuthenticationService;
    private UserValidator userValidator;
    
    @RequestMapping(value = "reset", method = { RequestMethod.POST })
    public @ResponseBody AuthenticationService.ResetStatus reset(@RequestParam String email) {
        return localAuthenticationService.resetPassword(email);
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

    @Autowired
    public void setLocalAuthenticationService(AuthenticationService localAuthenticationService) {
        this.localAuthenticationService = localAuthenticationService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }*/
}
