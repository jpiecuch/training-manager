package pl.jakubpiecuch.trainingmanager.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
public abstract class AbstractUserService implements UserService {

    protected EmailService emailService;
    protected AccountDao accountDao;
    private RememberMeServices rememberMeServices;

    public abstract boolean isValidCredentials(Account entity, UserDetails user);

    @Override
    public void signIn(HttpServletRequest request, HttpServletResponse response, UserDetails user) {
        SecurityUser securityUser = (SecurityUser) user;
        String username = securityUser.getSocial() != null ? String.format("%s:%s", securityUser.getSocial().getProviderId(), securityUser.getUsername()) : securityUser.getUsername();
        Account entity = accountDao.findByUniques(null, username, null);
        if (isValidCredentials(entity, user)) {
            List<String> roles = new ArrayList<String>();
            for (Role role : entity.getRoles()) {
                roles.addAll(Arrays.asList(role.getGrantedPermissions()));
            }
            AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()])));

            auth.setDetails(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
            rememberMeServices.loginSuccess(request, response, auth);
        }
    }


    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }


    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }
}
