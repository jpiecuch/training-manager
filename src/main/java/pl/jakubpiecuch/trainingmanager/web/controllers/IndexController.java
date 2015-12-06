package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialSignOnAdapter;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.util.Locale;

@Controller
@RequestMapping(value = {"/", "/index"})
public class IndexController {

    private SocialSignOnAdapter socialSignOnAdapter;
    private AuthenticationService authenticationService;
    
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model, @RequestParam(required = false, value = "social", defaultValue = "false") Boolean social, WebRequest request, Locale locale){
        if (social) {
            socialSignOnAdapter.signOn(request, locale);
        }
        try {
            model.addAttribute("user", authenticationService.signed());
        } catch(NotFoundException ex) {

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
