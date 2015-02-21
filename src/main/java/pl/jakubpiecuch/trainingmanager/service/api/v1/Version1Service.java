package pl.jakubpiecuch.trainingmanager.service.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.Dictionary;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowManager;
import pl.jakubpiecuch.trainingmanager.service.locale.LocaleService;
import pl.jakubpiecuch.trainingmanager.service.repository.*;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlan;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlanStarter;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Version1Service implements ApiVersionService {

    private String messageSourceFile;
    private CryptService cryptService;
    private Map<Provider.Type, UserService> userServices;
    private AuthenticationService authenticationService;
    private LocaleService localeService;
    private Map<ResourceService.Type, ResourceService> resourceServices;
    private Map<String, PropertiesConfiguration> propertiesConfigurations;
    private String[] langs;
    private Map<Flow.Hierarchy, FlowManager> flowManagers;
    private Dictionary dictionary;
    private Map<Repositories, Repository> repositories;
    private Map<Repositories, ReadRepository> readRepositories;
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, OrderResolver> orderResolverMap;
    private UserPlanStarter userPlanStarter;
    private UpdateManager<ExecutionDto> updateManager;

    @Override
    public void updateExecution(ExecutionDto execution) {
        updateManager.update(execution);
    }

    @Override
    public void startPlan(UserPlan userPlan) {
        userPlanStarter.start(userPlan);
    }

    @Override
    public Map<String, OrderResolver> orderResolvers() {
        return orderResolverMap;
    }

    @Override
    public PageResult retrieveFromRepository(Criteria criteria, Repositories type) {
        return readRepositories.get(type).read(criteria);
    }

    @Override
    public <T extends RepoObject> long storeInRepository(T object, Repositories type) {
        return repositories.get(type).create(object);
    }

    @Override
    public <T extends RepoObject> void updateInRepository(T object, Repositories type) {
        repositories.get(type).update(object);
    }

    @Override
    public void removeFromRepository(long id, Repositories type) {
        repositories.get(type).delete(id);
    }

    @Override
    public Equipment resolve(InputStream stream, Equipment.Type type) throws IOException {
        try {
            return (Equipment) mapper.readValue(stream, type.getTypeClass());
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Object dictionary(long id) {
        return dictionary.retrieve(id);
    }

    @Override
    public <T extends Flow> long createFlow(Flow.Hierarchy hierarchy, T flow) throws Exception {
        return flowManagers.get(hierarchy).save(flow);
    }

    @Override
    public <T extends Flow> List<T> children(Flow.Hierarchy hierarchy, Long id, boolean full) {
        return flowManagers.get(hierarchy.getChild()).children(id, full);
    }

    @Override
    public <T extends Flow> T flow(Flow.Hierarchy hierarchy, Long id, boolean full) {
        return (T) flowManagers.get(hierarchy).retrieve(id, full);
    }

    @Override
    public List<String> languages() {
        return Arrays.asList(langs);
    }

    @Override
    public void locale(HttpServletRequest request, HttpServletResponse response, String locale) {
        Assert.notNull(locale);
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
    public ResponseEntity resource(ResourceService.Type type, String key) throws Exception {
        final String handler = cryptService.decrypt(key, null);
        final ResourceService resourceService = resourceServices.get(type);
        if (resourceService.isCatalog(handler)) {
            return new ResponseEntity(new ArrayList<String>() {
                {
                    for(String path : resourceService.resources(handler)) {
                        add(cryptService.encrypt(path));
                    }
                }
            }, HttpStatus.OK);
        } else {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(resourceService.getMediaType(handler));
            return new ResponseEntity(resourceService.read(handler),headers, HttpStatus.OK);
        }
    }

    @Override
    public Object language(final String lang) throws Exception {
        final PropertiesConfiguration propertiesConfiguration = propertiesConfigurations.get(lang);
        if(propertiesConfiguration == null) {
            throw new NotFoundException();
        }
        return  new HashMap<String, String>() {
            {
                Iterator<String> keys = propertiesConfiguration.getKeys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    put(key, propertiesConfiguration.getString(key));
                }
            }
        };
    }


    @Autowired
    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    public void setResourceServices(Map<ResourceService.Type, ResourceService> resourceServices) {
        this.resourceServices = resourceServices;
    }

    @Required
    public void setUserServices(Map<Provider.Type, UserService> userServices) {
        this.userServices = userServices;
    }

    @Value("/bundles/web/web_%s.properties")
    public void setMessageSourceFile(String messageSourceFile) {
        this.messageSourceFile = messageSourceFile;
    }

    @Value("${app.langs}")
    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setPropertiesConfigurations(Map<String, PropertiesConfiguration> propertiesConfigurations) {
        this.propertiesConfigurations = propertiesConfigurations;
    }

    public void setReadRepositories(Map<Repositories, ReadRepository> readRepositories) {
        this.readRepositories = readRepositories;
    }

    public void setFlowManagers(Map<Flow.Hierarchy, FlowManager> flowManagers) {
        this.flowManagers = flowManagers;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setRepositories(Map<Repositories, Repository> repositories) {
        this.repositories = repositories;
    }

    public void setOrderResolverMap(Map<String, OrderResolver> orderResolverMap) {
        this.orderResolverMap = orderResolverMap;
    }

    public void setUserPlanStarter(UserPlanStarter userPlanStarter) {
        this.userPlanStarter = userPlanStarter;
    }

    public void setUpdateManager(UpdateManager<ExecutionDto> updateManager) {
        this.updateManager = updateManager;
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception{
        this.propertiesConfigurations = new HashMap<String, PropertiesConfiguration>() {
            {
                for(String lang : langs) {
                    put(lang, new PropertiesConfiguration(new File(getClass().getResource(String.format(messageSourceFile, lang)).toURI())));
                }
            }
        };
    }
}
