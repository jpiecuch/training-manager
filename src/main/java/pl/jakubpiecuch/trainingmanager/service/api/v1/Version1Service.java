package pl.jakubpiecuch.trainingmanager.service.api.v1;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseCommentDao;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.ExerciseComment;
import pl.jakubpiecuch.trainingmanager.service.support.SupportService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.DictionaryService;
import pl.jakubpiecuch.trainingmanager.service.locale.LocaleService;
import pl.jakubpiecuch.trainingmanager.service.plan.PlanService;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

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
    private AuthenticationService authenticationService;
    private LocaleService localeService;

    @Override
    public void locale(HttpServletRequest request, HttpServletResponse response, String locale) {
        localeService.update(request, response, StringUtils.parseLocaleString(locale));
    }

    @Override
    public void signIn(Authentication authentication) throws Exception {
        authenticationService.signIn(authentication);
    }

    @Override
    public void signOut() {
        authenticationService.signOut();
    }

    @Override
    public void signOn(Registration registration, Locale locale) throws Exception {
        userServices.get(registration.getProvider()).signOn(registration, locale);
    }

    @Override
    public Authentication signed() throws Exception {
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

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    @Required
    public void setUserServices(Map<Provider.Type, UserService> userServices) {
        this.userServices = userServices;
    }

    @Value("/bundles/web/web_%s.properties")
    public void setMessageSourceFile(String messageSourceFile) {
        this.messageSourceFile = messageSourceFile;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
