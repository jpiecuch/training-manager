package pl.jakubpiecuch.trainingmanager.service.user.authentication;

import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rico on 2014-12-13.
 */
public interface AuthenticationService {

    void signIn(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
    void signOut(HttpServletRequest request, HttpServletResponse response);
    Authentication signed();
}
