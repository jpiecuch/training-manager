package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.Locale;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface AuthenticationService extends UserDetailsService {

    boolean availability(String field, String value);
    enum ResetStatus { OK,USER_NOT_EXIST }
    ResetStatus resetPassword(String emial);
    boolean create(Users user, Locale locale);
    void socialSignUp(WebRequest request, boolean authenticate);
    boolean activate(String value);
}
