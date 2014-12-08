package pl.jakubpiecuch.trainingmanager.service.social;

import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.Response;

public interface SocialService {
   
    void publicMessage(String code);
    boolean createConnection(SecurityUser user, WebRequest request, Response<Authentication> response) throws Exception;
}
