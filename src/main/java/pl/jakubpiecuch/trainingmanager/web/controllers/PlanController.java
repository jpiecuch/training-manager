package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlanController {

    @RequestMapping(value = "plan/calendar.html", method = RequestMethod.GET)
    public String calendar(ModelMap map) {
        return "plan/calendar";
    }

    @RequestMapping(value = "plan/day.html", method = RequestMethod.GET)
    public String day() {
        return "plan/day";
    }
}
