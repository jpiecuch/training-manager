package pl.jakubpiecuch.gymhome.service.api;

import pl.jakubpiecuch.gymhome.service.api.v1.TranslatesService;
import pl.jakubpiecuch.gymhome.service.dictionary.Dictionary;
import pl.jakubpiecuch.gymhome.service.locale.LocaleService;
import pl.jakubpiecuch.gymhome.service.repository.ReadRepository;
import pl.jakubpiecuch.gymhome.service.repository.RepositoryType;
import pl.jakubpiecuch.gymhome.service.repository.StorageRepository;
import pl.jakubpiecuch.gymhome.service.repository.UpdatableRepository;
import pl.jakubpiecuch.gymhome.service.resource.ResourceService;
import pl.jakubpiecuch.gymhome.service.user.SignOnService;
import pl.jakubpiecuch.gymhome.service.user.UserManageService;
import pl.jakubpiecuch.gymhome.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.plan.UserPlanStarter;

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
