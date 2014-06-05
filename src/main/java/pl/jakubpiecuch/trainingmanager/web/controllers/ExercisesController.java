package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jakubpiecuch.trainingmanager.domain.Exercises.PartyMuscles;

@Controller
public class ExercisesController {

    @RequestMapping(value = "dictionary/exercises.html", method = RequestMethod.GET)
    public String list(ModelMap map) {
        return "exercises/list";
    }
    
    @RequestMapping(value = "exercises/preview.html", method = RequestMethod.GET)
    public String preview(@RequestParam(value = "partyMuscles") PartyMuscles partyMuscles, ModelMap map) {
        return "exercises/preview";
    }
}
