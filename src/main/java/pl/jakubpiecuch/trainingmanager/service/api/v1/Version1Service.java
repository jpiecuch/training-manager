package pl.jakubpiecuch.trainingmanager.service.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseCommentDao;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.ExerciseComment;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.calendar.CalendarService;
import pl.jakubpiecuch.trainingmanager.service.calendar.Event;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.day.DayService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.DictionaryService;
import pl.jakubpiecuch.trainingmanager.service.plan.PlanService;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.web.ui.DayExerciseUI;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;
import pl.jakubpiecuch.trainingmanager.web.validator.ExerciseValidator;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class Version1Service implements ApiVersionService {

    private final static String EXERCISE_SOCIAL_PREFIX = "exercise/";
    private String messageSourceFile;
    private CalendarService calendarService;
    private DayService dayService;
    private DictionaryService dictionaryService;
    private MessageSource messageSource;
    private AuthenticationService authenticationService;
    private Map<AuthenticationService.Social.Type, SocialService> socialServices;
    private CryptService cryptService;
    private ExerciseValidator exerciseValidator;
    private PlanService planService;
    private ExerciseCommentDao exerciseCommentDao;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object language(String lang) throws Exception {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(new File(getClass().getResource(String.format(messageSourceFile, lang)).toURI()));
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> keys = propertiesConfiguration.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            result.put(key, propertiesConfiguration.getString(key));
        }
        return result;
    }

    @Override
    public Object exercises(int first, int max, Exercise.PartyMuscles[] partyMuscles) {
        return dictionaryService.getExercises(first, max, partyMuscles);
    }

    @Override
    public void exercise(HttpServletRequest request) throws Exception {
        dictionaryService.save(objectMapper.readValue(request.getInputStream(), Exercise.class));
    }

    @Override
    public Object availableSocials() {
        return authenticationService.availableSocials();
    }

    @Override
    public Object saveDay(HttpServletRequest request) throws Exception {
        DayExercises d = objectMapper.readValue(request.getInputStream(), DayExerciseUI.class).toDayExercises();
        dayService.save(d);
        return DayExerciseUI.fromDayExercise(d);
    }

    @Override
    public Object exerciseProgress(long id) {
        return DayExerciseUI.fromDayExerciseList(dayService.exerciseProgress(AuthenticatedUserUtil.getUser(), id));
    }

    @Override
    public Object dayExercises(Date date) {
        return DayExerciseUI.fromDayExerciseList(dayService.day(AuthenticatedUserUtil.getUser(), date));
    }

    @Override
    public void socialPost(AuthenticationService.Social.Type type, long id) {
        socialServices.get(type).publicMessage(EXERCISE_SOCIAL_PREFIX + cryptService.encrypt(type.name(), String.valueOf(id)));
    }

    @Override
    public Object comments(long exerciseId, int first, int max) {
        return exerciseCommentDao.getPage(exerciseId, first, max);
    }

    @Override
    public Object equipmentTypes() {
        return Equipment.Type.values();
    }

    @Override
    public Object equimpentSet() {
        return dictionaryService.getEquipmentSet();
    }

    @Override
    public Object equipment(Equipment.Type type) {
        return dictionaryService.getEquipments(type);
    }

    @Override
    public Object plan(long id) {
        return planService.getPlan(id);
    }

    @Override
    public Object partyMuscles() {
        return Exercise.PartyMuscles.values();
    }

    @Override
    public Object events(Date start, Date end, Locale locale) {
        return calendarService.events(AuthenticatedUserUtil.getUser(), start, end, locale);
    }

    @Override
    public void moveEvent(HttpServletRequest request) throws Exception {
        calendarService.move(objectMapper.readValue(request.getInputStream(), Event.class));
    }

    @Override
    public void removeEvent(HttpServletRequest request) throws Exception {
        calendarService.remove(objectMapper.readValue(request.getInputStream(), Event.class));
    }

    @Override
    public Object createEvent(HttpServletRequest request) throws Exception {
        return calendarService.create(objectMapper.readValue(request.getInputStream(), Event.class), AuthenticatedUserUtil.getUser(), request.getLocale());
    }

    @Override
    public Object comment(HttpServletRequest request) throws Exception {
        ExerciseComment e = objectMapper.readValue(request.getInputStream(), ExerciseComment.class);
        e.setUser(AuthenticatedUserUtil.getUser());
        exerciseCommentDao.save(e);
        return exerciseCommentDao.findById(e.getId());
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

    @Autowired
    public void setExerciseValidator(ExerciseValidator exerciseValidator) {
        this.exerciseValidator = exerciseValidator;
    }

    @Autowired
    public void setPlanService(PlanService planService) {
        this.planService = planService;
    }

    public void setSocialServices(Map<AuthenticationService.Social.Type, SocialService> socialServices) {
        this.socialServices = socialServices;
    }

    @Autowired
    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

    @Autowired
    public void setExerciseCommentDao(ExerciseCommentDao exerciseCommentDao) {
        this.exerciseCommentDao = exerciseCommentDao;
    }

    @Value("/bundles/web/web_%s.properties")
    public void setMessageSourceFile(String messageSourceFile) {
        this.messageSourceFile = messageSourceFile;
    }

}
