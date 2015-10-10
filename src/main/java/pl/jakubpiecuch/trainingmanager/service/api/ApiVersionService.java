package pl.jakubpiecuch.trainingmanager.service.api;

import org.springframework.http.ResponseEntity;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.plan.UserPlan;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ApiVersionService {
    enum Version {
        v1
    }

    List<String> languages();
    ResponseEntity resource(ResourceService.Type type, String key) throws IOException;
    Object language(String lang);
    void locale(HttpServletRequest request, HttpServletResponse response, String locale);

    <T extends Criteria> PageResult retrieveFromRepository(T criteria, Repositories type);
    <T extends RepoObject> T uniqueFromRepository(Long id, Repositories type);
    <T extends RepoObject> long storeInRepository(T object, Repositories type);
    <T extends RepoObject> void updateInRepository(T object, Repositories type);
    void removeFromRepository(long id, Repositories type);

    Equipment resolve(InputStream stream, Equipment.Type type) throws IOException;

    Object dictionary(long id);
    Object dictionaries(Long[] ids);

    Map<String,OrderResolver> orderResolvers();

    void signIn(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
    void signOut(HttpServletRequest request, HttpServletResponse response);
    void signOn(Registration registration, Locale locale);
    Authentication signed();
    void startPlan(UserPlan userPlan);
    void updateExecution(ExecutionDto execution);
    void resetPassword(String email, Locale locale);
    void activate(String code);

}
