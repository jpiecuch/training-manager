package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

@Controller
@RequestMapping(value = {"/", "/index"})
public class IndexController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("isSignIn", AuthenticatedUserUtil.getAuthenticatedUserDetails() != null);
        return "index";
    }
}
