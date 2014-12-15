package pl.jakubpiecuch.trainingmanager.service.api.v1;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseCommentDao;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.ExerciseComment;
import pl.jakubpiecuch.trainingmanager.service.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.SupportService;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.DictionaryService;
import pl.jakubpiecuch.trainingmanager.service.plan.PlanService;
import pl.jakubpiecuch.trainingmanager.service.user.*;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;
import pl.jakubpiecuch.trainingmanager.web.validator.AuthenticationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Version1Service implements ApiVersionService {

    private final static String EXERCISE_SOCIAL_PREFIX = "exercise/";
    private String messageSourceFile;
    private DictionaryService dictionaryService;
    private SupportService<SocialProvider> supportService;
    private Map<SocialProvider.SocialType, SocialService> socialServices;
    private CryptService cryptService;
    private PlanService planService;
    private ExerciseCommentDao exerciseCommentDao;
    private Map<Provider.Type, UserService> userServices;
    private MapperService mapperService;
    private Validator validator;
    private AuthenticationService authenticationService;

    @Override
    public Response signIn(WebRequest request) throws Exception {
        return authenticationService.signIn(request);
    }

    @Override
    public Response signOut() {
        return authenticationService.signOut();
    }

    @Override
    public Response signOn(HttpServletRequest request) throws Exception {
        Response<Registration> response = new Response<Registration>();
        Registration registration = mapperService.getObject(request.getInputStream(), Registration.class, response);
        if (validator.isValid(registration, response, "")) {
            userServices.get(registration.getProvider()).signOn(registration, response, request.getLocale());
        }
        return response;
    }

    @Override
    public Response signed() throws Exception {
        return authenticationService.signed();
    }

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
        dictionaryService.save(mapperService.getObject(request.getInputStream(), Exercise.class));
    }

    @Override
    public Object availableSocials() {
        return supportService.supported();
    }

    @Override
    public void socialPost(SocialProvider.SocialType socialType, long id) {
        socialServices.get(socialType).publicMessage(EXERCISE_SOCIAL_PREFIX + cryptService.encrypt(socialType.name(), String.valueOf(id)));
    }

    @Override
    public Object comments(long exerciseId, int first, int max) {
        return exerciseCommentDao.getPage(exerciseId, first, max);
    }

    @Override
    public Object equipmentTypes() {
        return Equipment.Type.BENCH;
    }

    @Override
    public Object equipments(Integer[] type) {
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
    public Object comment(HttpServletRequest request) throws Exception {
        ExerciseComment e = mapperService.getObject(request.getInputStream(), ExerciseComment.class);
        e.setAccount(AuthenticatedUserUtil.getUser());
        exerciseCommentDao.save(e);
        return exerciseCommentDao.findById(e.getId());
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Autowired
    public void setSupportService(SupportService<SocialProvider> supportService) {
        this.supportService = supportService;
    }

    @Autowired
    public void setPlanService(PlanService planService) {
        this.planService = planService;
    }

    public void setSocialServices(Map<SocialProvider.SocialType, SocialService> socialServices) {
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

    @Autowired
    public void setMapperService(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    @Required
    public void setUserServices(Map<Provider.Type, UserService> userServices) {
        this.userServices = userServices;
    }

    @Value("/bundles/web/web_%s.properties")
    public void setMessageSourceFile(String messageSourceFile) {
        this.messageSourceFile = messageSourceFile;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
