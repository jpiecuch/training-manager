package pl.jakubpiecuch.trainingmanager.service.api;

import org.springframework.http.ResponseEntity;
import pl.jakubpiecuch.trainingmanager.dao.PhaseDao;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

public interface ApiVersionService {
    enum Version {v1}

    List<String> languages();
    ResponseEntity resource(ResourceService.Type type, String key) throws Exception;
    <T extends Flow> T flow(Flow.Hierarchy hierarchy, Long id);
    <T extends Flow> List<T> children(Flow.Hierarchy hierarchy, Long id);
    <T extends Flow> long createFlow(Flow.Hierarchy hierarchy, T flow);
    void signIn(Authentication authentication) throws Exception;
    void signOut();
    void signOn(Registration registration, Locale locale) throws Exception;
    Authentication signed() throws Exception;
    Object language(String lang) throws Exception;
    void locale(HttpServletRequest request, HttpServletResponse response, String locale);
}
