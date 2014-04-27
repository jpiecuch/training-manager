package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.Locale;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface AuthenticationService extends UserDetailsService {

    boolean availability(String field, String value);
    enum ResetStatus {OK,USER_NOT_EXIST}
    ResetStatus resetPassword(String emial);
    boolean create(Users user, Locale locale);
    boolean activate(String value);
}
