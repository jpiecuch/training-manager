package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises.PartyMusclesEnum;

@Controller
public class ExercisesController {

    @RequestMapping(value = "exercises/list.html", method = RequestMethod.GET)
    public String list(ModelMap map) {
        return "exercises/list";
    }
    
    @RequestMapping(value = "exercises/preview.html", method = RequestMethod.GET)
    public String preview(@RequestParam(value = "partyMuscles") PartyMusclesEnum partyMuscles, ModelMap map) {
        return "exercises/preview";
    }

    @RequestMapping(value = "exercises/details.html", method = RequestMethod.GET)
    public String details(@RequestParam(value = "id") Long id, ModelMap map) {
        return "exercises/details";
    }
    
    @RequestMapping(value = "exercises/new.html", method = RequestMethod.GET)
    public String neww(ModelMap map) {
        return "exercises/new";
    }
}
