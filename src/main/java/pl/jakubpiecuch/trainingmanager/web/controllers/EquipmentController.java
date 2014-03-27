package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EquipmentController {

    @RequestMapping(value = "equipment/list.html", method = RequestMethod.GET)
    public String list(ModelMap map) {
        return "equipment/list";
    }

}
