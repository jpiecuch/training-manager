package pl.jakubpiecuch.trainingmanager.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.web.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by Rico on 2014-11-22.
 */
public interface UserService {

    public void signOn(Registration request, Response<Registration> response, Locale locale) throws Exception;
    public void signIn(UserDetails user, WebRequest request, Response<Authentication> response) throws Exception;
    public UserDetails resolveDetails(InputStream stream) throws Exception;
}
