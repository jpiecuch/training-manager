package pl.jakubpiecuch.trainingmanager.service;

import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.web.Response;

/**
 * Created by Rico on 2014-12-13.
 */
public interface AuthenticationService {

    public Response<Authentication> signIn(WebRequest request) throws Exception;
    public Response<Authentication> signOut();
    public Response<Authentication> signed() throws Exception;
}
