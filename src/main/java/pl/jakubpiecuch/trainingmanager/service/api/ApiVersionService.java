package pl.jakubpiecuch.trainingmanager.service.api;

import pl.jakubpiecuch.trainingmanager.service.api.v1.TranslatesService;
import pl.jakubpiecuch.trainingmanager.service.dictionary.Dictionary;
import pl.jakubpiecuch.trainingmanager.service.locale.LocaleService;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.RepositoryType;
import pl.jakubpiecuch.trainingmanager.service.repository.StorageRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.UpdatableRepository;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.SignOnService;
import pl.jakubpiecuch.trainingmanager.service.user.UserManageService;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlanStarter;

public interface ApiVersionService {

    TranslatesService translates();
    UserManageService manageUser();
    AuthenticationService authentication();
    ReadRepository read(RepositoryType repository);
    StorageRepository store(RepositoryType repository);
    UpdatableRepository update(RepositoryType repository);
    ResourceService resources(ResourceService.Type type);
    UserPlanStarter starter();
    Dictionary dictionary();
    LocaleService locale();
    SignOnService signOn(Provider.Type provider);

    enum Version {
        v1
    }

}
