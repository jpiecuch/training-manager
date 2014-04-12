package pl.jakubpiecuch.trainingmanager.web.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface AuthenticationService extends UserDetailsService {
    enum ResetStatus {OK,USER_NOT_EXIST}
    ResetStatus resetPassword(String emial);
    boolean create(Users user);
}
