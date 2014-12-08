package pl.jakubpiecuch.trainingmanager.service.user.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;
import pl.jakubpiecuch.trainingmanager.service.user.AbstractUserService;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Rico on 2014-11-22.
 */
public class SocialUserServiceImpl extends AbstractUserService implements SocialUserService {

    private static final String OAUTH_PASSWORD = "oauth";
    private Map<SocialProvider.SocialType, SocialService> socialServices;

    @Override
    public UserDetails resolveDetails(InputStream stream) throws Exception {
        Authentication req = mapperService.getObject(stream, Authentication.class);
        SecurityUser details = new SecurityUser(null, req.getUsername(), req.getPassword(), req.getSocial(), null);
        return details;
    }

    @Override
    public boolean isValidCredentials(Account entity, UserDetails user, WebRequest request, Response<Authentication> response) throws Exception {
        SecurityUser securityUser = (SecurityUser) user;
        return socialServices.get(securityUser.getSocial()).createConnection(securityUser, request, response);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        Account account = usersDao.findByUniques(null, userId, null);
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        return new SecurityUser(account.getId(), account.getName(), account.getPassword(), null, account.getSalt());

    }

    @Override
    public void signOn(Registration registration, Response<Registration> response, Locale locale) throws Exception{
        Account account = new Account();
        account.setEmail(registration.getEmail());
        account.setConfig(new Account.Config.Builder().firstName(registration.getFirstName()).lastName(registration.getLastName()).build().toString());
        account.setName(registration.getUsername());
        account.setStatus(Account.Status.ACTIVE);
        account.setSalt(KeyGenerators.string().generateKey());
        account.setPassword(OAUTH_PASSWORD);

        usersDao.save(account);
        response.addEntity(new Registration(account));
    }

    @Required
    public void setSocialServices(Map<SocialProvider.SocialType, SocialService> socialServices) {
        this.socialServices = socialServices;
    }
}
