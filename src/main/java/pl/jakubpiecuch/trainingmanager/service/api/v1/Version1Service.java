package pl.jakubpiecuch.trainingmanager.service.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.Dictionary;
import pl.jakubpiecuch.trainingmanager.service.locale.LocaleService;
import pl.jakubpiecuch.trainingmanager.service.repository.*;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.local.LocalUserService;
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
import java.net.URISyntaxException;
import java.util.*;

public class Version1Service implements ApiVersionService {

    private static final char LIST_DELIMITER = ';';
    private String[] messageSourceFiles;
    private CryptService cryptService;
    private Map<Provider.Type, UserService> userServices;
    private AuthenticationService authenticationService;
    private LocaleService localeService;
    private Map<ResourceService.Type, ResourceService> resourceServices;
    private Map<String, List<PropertiesConfiguration>> propertiesConfigurations;
    private String[] langs;
    private Dictionary dictionary;
    private Map<Repositories, Repository> repositories;
    private Map<Repositories, ReadRepository> readRepositories;
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, OrderResolver> orderResolverMap;
    private UserPlanStarter userPlanStarter;
    private UpdatableRepository<ExecutionDto> updatableRepository;

    @Override
    public void activate(String code) {
        ((LocalUserService) userServices.get(Provider.Type.LOCAL)).activate(code);
    }

    @Override
    public void resetPassword(String email, Locale locale) {
        ((LocalUserService) userServices.get(Provider.Type.LOCAL)).password(email, locale);
    }

    @Override
    public void updateExecution(ExecutionDto execution) {
        updatableRepository.update(execution);
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
    public <T extends RepoObject> T uniqueFromRepository(Long id, Repositories type) {
        return (T) readRepositories.get(type).retrieve(id);
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
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Object dictionary(long id) {
        return dictionary.retrieve(id);
    }

    @Override
    public Object dictionaries(Long[] ids) {
        return dictionary.retrieve(ids);
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
    public void signIn(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        authenticationService.signIn(request, response, authentication);
    }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.signOut(request, response);
    }

    @Override
    public void signOn(Registration registration, Locale locale) {
        userServices.get(registration.getProvider()).signOn(registration, locale);
    }

    @Override
    public Authentication signed() {
        return authenticationService.signed();
    }

    @Override
    public ResponseEntity resource(ResourceService.Type type, String key) throws IOException {
        final String handler = cryptService.decrypt(key, null);
        final ResourceService resourceService = resourceServices.get(type);
        if (resourceService.isCatalog(handler)) {
            return new ResponseEntity(Lists.transform(resourceService.resources(handler), new Function<String, String>() {
                @Override
                public String apply(String input) {
                    return cryptService.encrypt(input);
                }
            }), HttpStatus.OK);
        } else {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(resourceService.getMediaType(handler));
            return new ResponseEntity(resourceService.read(handler), headers, HttpStatus.OK);
        }
    }

    @Override
    public Object language(final String lang) {
        final List<PropertiesConfiguration> configurations = propertiesConfigurations.get(lang);
        if (CollectionUtils.isEmpty(configurations)) {
            throw new NotFoundException();
        }
        Map<String, String> result = new HashMap<String, String>();
        for (final PropertiesConfiguration propertiesConfiguration : configurations) {
            result.putAll(Maps.toMap(propertiesConfiguration.getKeys(), new Function<String, String>() {
                @Override
                public String apply(String input) {
                    return propertiesConfiguration.getString(input);
                }
            }));
        }
        return result;
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

    public void setMessageSourceFiles(String[] messageSourceFiles) {
        this.messageSourceFiles = messageSourceFiles;
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setPropertiesConfigurations(Map<String, List<PropertiesConfiguration>> propertiesConfigurations) {
        this.propertiesConfigurations = propertiesConfigurations;
    }

    public void setReadRepositories(Map<Repositories, ReadRepository> readRepositories) {
        this.readRepositories = readRepositories;
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

    public void setUpdatableRepository(UpdatableRepository<ExecutionDto> updatableRepository) {
        this.updatableRepository = updatableRepository;
    }

    @PostConstruct
    protected void afterPropertiesSet() throws URISyntaxException, ConfigurationException {
        this.propertiesConfigurations = new HashMap<String, List<PropertiesConfiguration>>();
        for (String lang : langs) {
            List<PropertiesConfiguration> configurations = new ArrayList<PropertiesConfiguration>();
            for (String messageSourceFile : messageSourceFiles) {
                PropertiesConfiguration configuration = new PropertiesConfiguration();
                configuration.setListDelimiter(LIST_DELIMITER);
                configuration.setFile(new File(getClass().getResource(String.format(messageSourceFile, lang)).toURI()));
                configuration.load();
                configurations.add(configuration);
            }
            this.propertiesConfigurations.put(lang, configurations);
        }
    }
}
