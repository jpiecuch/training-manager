package pl.jakubpiecuch.trainingmanager.service.user.authentication;

import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;

import java.io.IOException;

/**
 * Created by Rico on 2014-12-13.
 */
public interface AuthenticationService {

    public void signIn(Authentication authentication);
    public void signOut();
    public Authentication signed() throws IOException;
}
