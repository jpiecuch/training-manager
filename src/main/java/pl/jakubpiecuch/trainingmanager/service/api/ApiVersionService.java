package pl.jakubpiecuch.trainingmanager.service.api;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.ResponseEntity;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.PhaseDao;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.OrderResolver;
import pl.jakubpiecuch.trainingmanager.service.execution.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.service.execution.session.SessionExecutionCriteria;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ApiVersionService {
    enum Version {v1}

    List<String> languages();
    ResponseEntity resource(ResourceService.Type type, String key) throws Exception;
    Object language(String lang) throws Exception;
    void locale(HttpServletRequest request, HttpServletResponse response, String locale);

    <T extends Flow> T flow(Flow.Hierarchy hierarchy, Long id, boolean full);
    <T extends Flow> List<T> children(Flow.Hierarchy hierarchy, Long id, boolean full);
    <T extends Flow> long createFlow(Flow.Hierarchy hierarchy, T flow) throws Exception;

    <T extends Criteria> PageResult retrieveFromRepository(T criteria, Repositories type);
    <T extends RepoObject> long storeInRepository(T object, Repositories type);
    <T extends RepoObject> void updateInRepository(T object, Repositories type);
    void removeFromRepository(long id, Repositories type);

    Equipment resolve(InputStream stream, Equipment.Type type) throws IOException;

    PageResult<ExecutionDto> executions(SessionExecutionCriteria sessionExecutionCriteria);

    Object dictionary(long id);

    Map<String,OrderResolver> orderResolvers();

    void signIn(Authentication authentication) throws Exception;
    void signOut();
    void signOn(Registration registration, Locale locale) throws Exception;
    Authentication signed() throws Exception;


}
