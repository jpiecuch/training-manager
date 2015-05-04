package pl.jakubpiecuch.trainingmanager.service.user.social;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import java.util.Locale;

public class SocialSignOnAdapter {
    private ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();
    private AccountDao accountDao;
    private UserService userService;
    
   public void signOn(WebRequest request, Locale locale) {
       Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
       if (connection != null) {
           UserProfile profile = connection.fetchUserProfile();

           Registration registration = new Registration();
           registration.setUsername(String.format(SecurityUser.SOCIAL_USERNAME_FORMAT, connection.getKey().getProviderId(), connection.getKey().getProviderUserId()));
           registration.setEmail(profile.getEmail());
           registration.setFirstName(profile.getFirstName());
           registration.setLastName(profile.getLastName());
           registration.setSocial(SocialProvider.SocialType.valueOf(connection.getKey().getProviderId().toUpperCase()));
           registration.setPassword(connection.createData().getAccessToken());

           userService.signOn(registration, locale);
           providerSignInUtils.doPostSignUp(profile.getName(), request);
           Account entity = accountDao.findByUniques(null, String.format(SecurityUser.SOCIAL_USERNAME_FORMAT, connection.getKey().getProviderId(), connection.getKey().getProviderUserId()), null);
           WebUtil.authenticate(new SecurityUser(entity.getId(), entity.getName(), entity.getPassword(), SocialProvider.SocialType.valueOf(connection.getKey().getProviderId().toUpperCase()),
                   CollectionUtils.isNotEmpty(entity.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(entity.getGrantedPermissions().toArray(new String[entity.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES));
       }
   }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
