package pl.jakubpiecuch.trainingmanager.service.api;

import org.springframework.http.ResponseEntity;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.api.v1.TranslatesService;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.RepositoryType;
import pl.jakubpiecuch.trainingmanager.service.repository.StorageRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.UpdatableRepository;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.UserManageService;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

public interface ApiVersionService {

    TranslatesService translates();
    UserManageService manageUser();
    AuthenticationService authentication();
    Map<RepositoryType, ReadRepository> read();
    StorageRepository store(RepositoryType repository);
    UpdatableRepository update(RepositoryType repository);

    ResponseEntity resource(ResourceService.Type type, String key) throws IOException;

    void locale(HttpServletRequest request, HttpServletResponse response, String locale);

    Equipment resolve(InputStream stream, Equipment.Type type) throws IOException;

    Object dictionary(long id);

    Object dictionaries(Long[] ids);

    void signOn(Registration registration, Locale locale);

    void startPlan(UserPlan userPlan);

    enum Version {
        v1
    }

}
