package pl.jakubpiecuch.gymhome.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rico on 2014-11-22.
 */
public interface UserService extends SignOnService {

    void signIn(HttpServletRequest request, HttpServletResponse response, UserDetails user);
    UserDetails resolveDetails(Authentication authentication);
}
