package pl.jakubpiecuch.trainingmanager.service.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.Dictionary;
import pl.jakubpiecuch.trainingmanager.service.locale.LocaleService;
import pl.jakubpiecuch.trainingmanager.service.repository.*;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.UserManageService;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlan;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlanStarter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

public class Version1Service implements ApiVersionService {

    private CryptService cryptService;
    private Map<Provider.Type, UserService> userServices;
    private AuthenticationService authenticationService;
    private LocaleService localeService;
    private Map<ResourceService.Type, ResourceService> resourceServices;
    private Dictionary dictionary;
    private Map<RepositoryType, Repository> repositories;
    private Map<RepositoryType, ReadRepository> readRepositories;
    private Map<RepositoryType, UpdatableRepository> updateRepositories;
    private ObjectMapper mapper = new ObjectMapper();
    private UserPlanStarter userPlanStarter;
    private TranslatesService translatesService;

    @Override
    public TranslatesService translates() {
        return translatesService;
    }

    @Override
    public UserManageService manageUser() {
        return (UserManageService) userServices.get(Provider.Type.LOCAL);
    }

    @Override
    public AuthenticationService authentication() {
        return authenticationService;
    }

    @Override
    public void startPlan(UserPlan userPlan) {
        userPlanStarter.start(userPlan);
    }

    @Override
    public Map<RepositoryType, ReadRepository> read() {
        return readRepositories;
    }

    @Override
    public StorageRepository store(RepositoryType repository) {
        return repositories.get(repository);
    }

    @Override
    public UpdatableRepository update(RepositoryType repository) {
        return updateRepositories.get(repository);
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
    public void locale(HttpServletRequest request, HttpServletResponse response, String locale) {
        Assert.notNull(locale);
        localeService.update(request, response, StringUtils.parseLocaleString(locale));
    }

    @Override
    public void signOn(Registration registration, Locale locale) {
        userServices.get(registration.getProvider()).signOn(registration, locale);
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


    public void setTranslatesService(TranslatesService translatesService) {
        this.translatesService = translatesService;
    }

    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

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

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setReadRepositories(Map<RepositoryType, ReadRepository> readRepositories) {
        this.readRepositories = readRepositories;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setRepositories(Map<RepositoryType, Repository> repositories) {
        this.repositories = repositories;
    }

    public void setUserPlanStarter(UserPlanStarter userPlanStarter) {
        this.userPlanStarter = userPlanStarter;
    }

    public void setUpdateRepositories(Map<RepositoryType, UpdatableRepository> updateRepositories) {
        this.updateRepositories = updateRepositories;
    }
}
