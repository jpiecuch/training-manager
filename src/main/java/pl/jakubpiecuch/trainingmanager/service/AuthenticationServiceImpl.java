package pl.jakubpiecuch.trainingmanager.service;

import org.apache.commons.io.IOUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Rico on 2014-12-13.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private MapperService mapperService;
    private Validator validator;
    private Map<Provider.Type, UserService> userServices;
    private UsersDao usersDao;

    @Override
    public Response<Authentication> signIn(WebRequest request) throws Exception {
        Response<Authentication> response = new Response<Authentication>();
        HttpServletRequest httpServletRequest = (HttpServletRequest) ((NativeWebRequest) request).getNativeRequest();
        String input = IOUtils.toString(httpServletRequest.getInputStream());
        Authentication authentication = mapperService.getObject(IOUtils.toInputStream(input), Authentication.class, response);
        if (validator.isValid(authentication, response, "")) {
            UserDetails details = userServices.get(authentication.getProvider()).resolveDetails(IOUtils.toInputStream(input));
            userServices.get(authentication.getProvider()).signIn(details, request, response);
        }
        return response;
    }

    @Override
    public Response<Authentication> signOut() {
        WebUtil.invalidate();
        return new Response();
    }

    @Override
    public Response<Authentication> signed() throws Exception {
        SecurityUser authenticatedUserDetails = AuthenticatedUserUtil.getAuthenticatedUserDetails();
        Response<Authentication> response = new Response<Authentication>();
        if (authenticatedUserDetails != null) {
            Account account = usersDao.findByUniques(authenticatedUserDetails.getId(), null, null);
            response.addEntity(new Authentication(account));
        } else {
            response.addError(new Response.Error.Builder(Response.Error.Type.RUNTIME).value(Response.Error.Code.NOT_SIGNED).build());
        }
        return response;
    }

    public void setMapperService(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    public void setUserServices(Map<Provider.Type, UserService> userServices) {
        this.userServices = userServices;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }
}
