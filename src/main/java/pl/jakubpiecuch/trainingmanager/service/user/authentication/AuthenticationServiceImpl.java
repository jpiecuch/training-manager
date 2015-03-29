package pl.jakubpiecuch.trainingmanager.service.user.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Rico on 2014-12-13.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String ANONYMOUS_USER_PRINCIPAL = "anonymousUser";
    private static final String ANONYMOUS_USER_KEY = "anonymousKey";
    private static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";

    private Validator validator;
    private Map<Provider.Type, UserService> userServices;
    private AccountDao accountDao;
    private LogoutHandler logoutHandler;

    @Override
    public void signIn(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        validator.validate(authentication, new BeanPropertyBindingResult(authentication, Authentication.BEAN_NAME));
        UserDetails details = userServices.get(authentication.getProvider()).resolveDetails(authentication);
        userServices.get(authentication.getProvider()).signIn(request, response, details);
    }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(ANONYMOUS_USER_KEY, ANONYMOUS_USER_PRINCIPAL, AuthorityUtils.createAuthorityList(ANONYMOUS_ROLE)));

    }

    @Override
    public Authentication signed() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof String) {
            throw new NotFoundException();
        }
        return new Authentication(accountDao.findByUniques(((SecurityUser) object).getId(), ((SecurityUser) object).getUsername(), null));
    }

    public void setUserServices(Map<Provider.Type, UserService> userServices) {
        this.userServices = userServices;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setLogoutHandler(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }
}
