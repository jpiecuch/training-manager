package pl.jakubpiecuch.trainingmanager.service.user.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2014-12-13.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String ANONYMOUS_USER_PRINCIPAL = "anonymousUser";
    private static final String ANONYMOUS_USER_KEY = "anonymousKey";
    private static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";
    private static final List<GrantedAuthority> ANONYMOUS_USER_AUTHORITY = new ArrayList<GrantedAuthority>() {
        {
            add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return ANONYMOUS_ROLE;
                }
            });
        }
    };

    private Validator validator;
    private Map<Provider.Type, UserService> userServices;
    private AccountDao accountDao;

    @Override
    public void signIn(Authentication authentication) throws Exception {
        validator.validate(authentication, new BeanPropertyBindingResult(authentication, Authentication.BEAN_NAME));
        UserDetails details = userServices.get(authentication.getProvider()).resolveDetails(authentication);
        userServices.get(authentication.getProvider()).signIn(details);
    }

    @Override
    public void signOut() {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(ANONYMOUS_USER_KEY, ANONYMOUS_USER_PRINCIPAL, ANONYMOUS_USER_AUTHORITY));
    }

    @Override
    public Authentication signed() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof String) {
            throw new NotFoundException();
        }
        return new Authentication(accountDao.findByUniques(((SecurityUser) object).getId(), null, null));
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
}
