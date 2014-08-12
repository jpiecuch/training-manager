package pl.jakubpiecuch.trainingmanager.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.day.DayService;
import pl.jakubpiecuch.trainingmanager.web.ui.DayExerciseUI;
import pl.jakubpiecuch.trainingmanager.web.ui.SeriesUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/result")
public class ResultController {

    private CryptService cryptService;
    private DayService dayService;
    private MessageSource messageSource;

    @RequestMapping(value = "{code}")
    public String  exercise(@PathVariable final String code, Locale locale, Model model) {
        String id = cryptService.decrypt(code, 1);
        DayExerciseUI dayExercise = DayExerciseUI.fromDayExercise(dayService.exercise(Long.valueOf(id)), messageSource, locale);
        model.addAttribute("exercise", dayExercise);

        int mod4 = dayExercise.getSeries().length % 4;
        int mod3 = dayExercise.getSeries().length % 3;
        int chunkSize = mod4 % 4 == 0 ? 4 : mod3 % 3 == 0 ? 3 : dayExercise.getSeries().length < 3 ? dayExercise.getSeries().length : mod4 > mod3 ? 4 : 3;
        List<SeriesUI[]> rows = new ArrayList<SeriesUI[]>();
        for(int i = 0; i < dayExercise.getSeries().length;) {
            int length = i + chunkSize > dayExercise.getSeries().length ? dayExercise.getSeries().length - i : chunkSize;
            SeriesUI[] row = new SeriesUI[length];
            for(int j = 0; j < length; j++) {
                row[j] = dayExercise.getSeries()[i+j];
            }
            i += chunkSize;
            rows.add(row);
        }
        model.addAttribute("rows", rows);
        model.addAttribute("chunk", chunkSize);
        return "result/exercise";
    }

    @Autowired
    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

    @Autowired
    public void setDayService(DayService dayService) {
        this.dayService = dayService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
