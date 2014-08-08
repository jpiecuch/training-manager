package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/result")
public class ResultController {

    @RequestMapping(value = "{code}")
    public String exercise() {
        return "result/exercise";
    }
}
