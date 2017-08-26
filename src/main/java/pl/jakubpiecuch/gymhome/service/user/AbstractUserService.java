package pl.jakubpiecuch.gymhome.service.user;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.Role;
import pl.jakubpiecuch.gymhome.service.mail.EmailService;
import pl.jakubpiecuch.gymhome.service.repository.Repository;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;

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
    protected Repository<Account, AccountCriteria> repository;
    protected Repository<Role, RoleCriteria> roleRepository;
    private RememberMeServices rememberMeServices;

    public abstract boolean isValidCredentials(Account entity, UserDetails user);

    @Override
    public void signIn(HttpServletRequest request, HttpServletResponse response, UserDetails user) {
        SecurityUser securityUser = (SecurityUser) user;
        //TODO: this is not good enough
        String username = securityUser.getSocial() != null ? String.format(SecurityUser.SOCIAL_USERNAME_FORMAT, securityUser.getSocial().getProviderId(), securityUser.getUsername()) : securityUser.getUsername();
        Account entity = findUser(username);
        if (isValidCredentials(entity, user)) {
            List<String> roles = new ArrayList<String>();
            for (Role role : entity.getRoles()) {
                roles.addAll(Arrays.asList(role.getGrantedPermissions()));
            }
            AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            auth.setDetails(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
            rememberMeServices.loginSuccess(request, response, auth);
        }
    }

    protected Account findUser(String username) {
        PageResult<Account> result = repository.page(new AccountCriteria().addNameRestrictions(username));
        if (result.getCount() > 0) {
            return result.getResult().get(0);
        }
        return null;
    }


    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }


    public void setRepository(Repository<Account, AccountCriteria> repository) {
        this.repository = repository;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    public void setRoleRepository(Repository<Role, RoleCriteria> roleRepository) {
        this.roleRepository = roleRepository;
    }
}
