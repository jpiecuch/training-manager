package pl.jakubpiecuch.trainingmanager.service.user.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

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
    public void signIn(Authentication authentication) throws Exception {
        validator.validate(authentication, new BeanPropertyBindingResult(authentication, "authentication"));
        UserDetails details = userServices.get(authentication.getProvider()).resolveDetails(authentication);
        userServices.get(authentication.getProvider()).signIn(details);
    }

    @Override
    public void signOut() {
        WebUtil.invalidate();
    }

    @Override
    public Authentication signed() throws Exception {
        SecurityUser authenticatedUserDetails = AuthenticatedUserUtil.getAuthenticatedUserDetails();
        Account account = usersDao.findByUniques(authenticatedUserDetails.getId(), null, null);
        return new Authentication(account);
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
