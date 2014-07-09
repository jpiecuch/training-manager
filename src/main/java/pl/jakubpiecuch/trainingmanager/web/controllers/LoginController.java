package pl.jakubpiecuch.trainingmanager.web.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

@Controller
public class LoginController {

    @RequestMapping(value = {"login.html", "logout.html", "failure.html"}, method = RequestMethod.GET)
    public String login(Model map, HttpServletRequest request) {
        if (!AuthenticatedUserUtil.isAuthenticated()) {
            return "login";
        }
        return "redirect:index.html";
    }
}
