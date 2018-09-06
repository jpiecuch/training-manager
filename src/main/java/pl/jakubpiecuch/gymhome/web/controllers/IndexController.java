package pl.jakubpiecuch.gymhome.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.gymhome.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.gymhome.service.user.social.SocialSignOnAdapter;
import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;

import java.util.Locale;

@Controller
@RequestMapping(value = {"/", "/index"})
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private SocialSignOnAdapter socialSignOnAdapter;
    private AuthenticationService authenticationService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model, @RequestParam(required = false, value = "social", defaultValue = "false") Boolean social, WebRequest request, Locale locale) {
        if (social) {
            socialSignOnAdapter.signOn(request, locale);
        }
        try {
            model.addAttribute("user", authenticationService.signed());
        } catch (NotFoundException ex) {
            LOGGER.debug("User not found", ex);
        }
        return "index";
    }

    @Autowired
    public void setSocialSignOnAdapter(SocialSignOnAdapter socialSignOnAdapter) {
        this.socialSignOnAdapter = socialSignOnAdapter;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
