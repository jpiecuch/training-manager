package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/", "index.html"})
public class IndexController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map){
        return "index";
    }
}
