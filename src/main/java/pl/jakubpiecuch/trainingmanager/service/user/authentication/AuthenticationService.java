package pl.jakubpiecuch.trainingmanager.service.user.authentication;

import pl.jakubpiecuch.trainingmanager.service.user.Authentication;

/**
 * Created by Rico on 2014-12-13.
 */
public interface AuthenticationService {

    public void signIn(Authentication authentication) throws Exception;
    public void signOut();
    public Authentication signed() throws Exception;
}
