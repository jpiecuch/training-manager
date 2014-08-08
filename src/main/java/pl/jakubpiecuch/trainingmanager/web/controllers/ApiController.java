package pl.jakubpiecuch.trainingmanager.web.controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.service.calendar.CalendarService;
import pl.jakubpiecuch.trainingmanager.service.calendar.Event;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.day.DayService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.DictionaryService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.EquipmentSet;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService.Social;
import pl.jakubpiecuch.trainingmanager.web.authentication.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.ui.DayExerciseUI;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
    
    private CalendarService calendarService;
    private DayService dayService;
    private DictionaryService dictionaryService;
    private MessageSource messageSource;
    private AuthenticationService authenticationService;
    private String messageSourceFile;
    private Map<Social.Type, SocialService> socialServices;
    private CryptService cryptService;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    @RequestMapping(value = "languages/{lang}", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> languages(@PathVariable String lang) throws ConfigurationException, URISyntaxException {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(new File(getClass().getResource(String.format(messageSourceFile, lang)).toURI()));
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> keys = propertiesConfiguration.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            result.put(key, propertiesConfiguration.getString(key));
        }
        return result;
    }

    @RequestMapping(value = "dictionary/exercises", method = RequestMethod.GET)
    public @ResponseBody List<Exercises> exercises() {
        return dictionaryService.getExercises();
    }
    
    @RequestMapping(value = "dictionary/social", method = RequestMethod.GET)
    public @ResponseBody List<Social> socials() {
        return authenticationService.availableSocials();
    }

    @RequestMapping(value = "calendar/events/start/{start}/end/{end}", method = RequestMethod.GET)
    public @ResponseBody List<Event> calendarEvents(@PathVariable Date start, @PathVariable Date end) {
        return calendarService.events(AuthenticatedUserUtil.getUser(), start, end);
    }

    @RequestMapping(value = "exercise/save", method = RequestMethod.POST)
    public @ResponseBody void saveDay(@RequestBody DayExerciseUI dayExercises) {
        dayService.save(dayExercises.toDayExercises());
    }

    @RequestMapping(value = "exercise/result/{code}", method = RequestMethod.GET)
    public @ResponseBody DayExerciseUI exercise(@PathVariable final String code, Locale locale) {
        String id = cryptService.decrypt(code, 1);
        return DayExerciseUI.fromDayExercise(dayService.exercise(Long.valueOf(id)), messageSource, locale);
    }
    
    @RequestMapping(value = "exercise/{id}/progress", method = RequestMethod.GET)
    public @ResponseBody List<DayExerciseUI> exerciseProgress(@PathVariable final Long id, Locale locale) {
        return DayExerciseUI.fromDayExerciseList(dayService.exerciseProgress(AuthenticatedUserUtil.getUser(), id), messageSource, locale);
    }
    
    @RequestMapping(value = "exercise/{date}", method = RequestMethod.GET)
    public @ResponseBody List<DayExerciseUI> dayExercises(@PathVariable Date date, Locale locale) {
        return DayExerciseUI.fromDayExerciseList(dayService.day(AuthenticatedUserUtil.getUser(), date), messageSource, locale);
    }

    @RequestMapping(value = "dictionary/equipment", method = RequestMethod.GET)
    public @ResponseBody EquipmentSet availableEquipment() {
        return dictionaryService.getEquipmentSet();
    }
    
    @RequestMapping(value = "dictionary/{type}/equipment", method = RequestMethod.GET)
    public @ResponseBody List<Equipment> equipment(@PathVariable Equipment.Type type) {
        return dictionaryService.getEquipments(type);
    }
    
    @RequestMapping(value = "dictionary/partymuscles", method = RequestMethod.GET)
    public @ResponseBody Exercises.PartyMuscles[] partyMuscles() {
        return Exercises.PartyMuscles.values();
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
        return calendarService.create(event, AuthenticatedUserUtil.getUser());
    }
    
    @RequestMapping(value = "social/{type}/{id}", method = RequestMethod.POST)
    public @ResponseBody void socialPost(@PathVariable Social.Type type, @PathVariable Long id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(SocialService.Params.CODE, cryptService.encrypt(type.name(), id.toString()));
        socialServices.get(type).post(params);
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

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setSocialServices(Map<Social.Type, SocialService> socialServices) {
        this.socialServices = socialServices;
    }

    @Autowired
    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

    @Value("/bundles/web/web_%s.properties")
    public void setMessageSourceFile(String messageSourceFile) {
        this.messageSourceFile = messageSourceFile;
    }
}
