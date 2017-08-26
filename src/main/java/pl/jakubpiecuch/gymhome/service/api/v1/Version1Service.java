package pl.jakubpiecuch.gymhome.service.api.v1;

import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.dictionary.Dictionary;
import pl.jakubpiecuch.gymhome.service.locale.LocaleService;
import pl.jakubpiecuch.gymhome.service.repository.*;
import pl.jakubpiecuch.gymhome.service.resource.ResourceService;
import pl.jakubpiecuch.gymhome.service.user.SignOnService;
import pl.jakubpiecuch.gymhome.service.user.UserManageService;
import pl.jakubpiecuch.gymhome.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.plan.UserPlanStarter;

import java.util.Map;

public class Version1Service implements ApiVersionService {

    private Map<Provider.Type, SignOnService> signOnServices;
    private AuthenticationService authenticationService;
    private LocaleService localeService;
    private Map<ResourceService.Type, ResourceService> resourceServices;
    private Dictionary dictionary;
    private Map<RepositoryType, Repository> repositories;
    private Map<RepositoryType, ReadRepository> readRepositories;
    private Map<RepositoryType, UpdatableRepository> updateRepositories;
    private UserPlanStarter userPlanStarter;
    private TranslatesService translatesService;

    @Override
    public TranslatesService translates() {
        return translatesService;
    }

    @Override
    public UserManageService manageUser() {
        return (UserManageService) signOnServices.get(Provider.Type.LOCAL);
    }

    @Override
    public AuthenticationService authentication() {
        return authenticationService;
    }

    @Override
    public ReadRepository read(RepositoryType repository) {
        return readRepositories.get(repository);
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
    public ResourceService resources(ResourceService.Type type) {
        return resourceServices.get(type);
    }

    @Override
    public UserPlanStarter starter() {
        return userPlanStarter;
    }

    @Override
    public Dictionary dictionary() {
        return dictionary;
    }

    @Override
    public LocaleService locale() {
        return localeService;
    }

    @Override
    public SignOnService signOn(Provider.Type provider) {
        return signOnServices.get(provider);
    }


    public void setTranslatesService(TranslatesService translatesService) {
        this.translatesService = translatesService;
    }

    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    public void setResourceServices(Map<ResourceService.Type, ResourceService> resourceServices) {
        this.resourceServices = resourceServices;
    }

    public void setSignOnServices(Map<Provider.Type, SignOnService> signOnServices) {
        this.signOnServices = signOnServices;
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
