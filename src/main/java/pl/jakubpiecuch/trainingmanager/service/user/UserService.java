package pl.jakubpiecuch.trainingmanager.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Rico on 2014-11-22.
 */
public interface UserService {

    public void signOn(Registration request, Locale locale);
    public void signIn(HttpServletRequest request, HttpServletResponse response, UserDetails user);
    public UserDetails resolveDetails(Authentication authentication);
}
