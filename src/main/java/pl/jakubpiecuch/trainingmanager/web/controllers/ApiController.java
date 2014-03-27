package pl.jakubpiecuch.trainingmanager.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.service.calendar.CalendarService;
import pl.jakubpiecuch.trainingmanager.service.calendar.Event;
import pl.jakubpiecuch.trainingmanager.service.day.DayService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.DictionaryService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.EquipmentSet;
import pl.jakubpiecuch.trainingmanager.web.services.AuthenticatedUserUtil;
import pl.jakubpiecuch.trainingmanager.web.ui.DayExerciseUI;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
    
    private CalendarService calendarService;
    private DayService dayService;
    private DictionaryService dictionaryService;
    private MessageSource messageSource;
    
    @InitBinder
    private void initBinder(WebDataBinder binder) {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "dictionary/exercises", method = RequestMethod.GET)
    public @ResponseBody List<Exercises> exercises() {
        return dictionaryService.getExercises();
    }

    @RequestMapping(value = "calendar/events", method = RequestMethod.GET)
    public @ResponseBody List<Event> calendarEvents() {
        return calendarService.getEvents(AuthenticatedUserUtil.getAuthenticatedUserDetails().getId());
    }

    @RequestMapping(value = "exercise/save", method = RequestMethod.POST)
    public @ResponseBody void saveDay(@RequestBody DayExerciseUI dayExercises) {
        dayService.save(dayExercises.toDayExercises());
    }
    
    @RequestMapping(value = "exercise/{id}/progress", method = RequestMethod.GET)
    public @ResponseBody List<DayExerciseUI> exerciseProgress(@PathVariable final Long id, Locale locale) {
        return DayExerciseUI.fromDayExerciseList(dayService.getProgress(AuthenticatedUserUtil.getAuthenticatedUserDetails().getId(), id), messageSource, locale);
    }
    
    @RequestMapping(value = "exercise/{date}", method = RequestMethod.GET)
    public @ResponseBody List<DayExerciseUI> dayExercises(@PathVariable Date date, Locale locale) {
        return DayExerciseUI.fromDayExerciseList(dayService.getDay(AuthenticatedUserUtil.getAuthenticatedUserDetails().getId(), date), messageSource, locale);
    }

    @RequestMapping(value = "dictionary/equipment", method = RequestMethod.GET)
    public @ResponseBody EquipmentSet availableEquipment() {
        return dictionaryService.getEquipmentSet();
    }
    
    @RequestMapping(value = "dictionary/{type}/equipment", method = RequestMethod.GET)
    public @ResponseBody List<Equipment> equipment(@PathVariable Equipment.Type type) {
        return dictionaryService.getEquipments(type);
    }

    @RequestMapping(value = "calendar/event/move", method = RequestMethod.POST)
    public @ResponseBody void moveEvent(@RequestBody final Event event) throws Exception{
        calendarService.move(event);
    }

    @RequestMapping(value = "calendar/event/remove", method = RequestMethod.POST)
    public @ResponseBody void removeEvent(@RequestBody final Event event) {
        calendarService.remove(event);
    }
    
    @RequestMapping(value = "calendar/event/create", method = RequestMethod.POST)
    public @ResponseBody Event createEvent(@RequestBody final Event event) throws Exception {
        return calendarService.create(event, AuthenticatedUserUtil.getAuthenticatedUserDetails().getId());
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Autowired
    public void setDayService(DayService dayService) {
        this.dayService = dayService;
    } 
}
