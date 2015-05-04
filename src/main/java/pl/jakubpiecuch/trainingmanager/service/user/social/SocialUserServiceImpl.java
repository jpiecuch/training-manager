package pl.jakubpiecuch.trainingmanager.service.user.social;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;
import pl.jakubpiecuch.trainingmanager.service.user.AbstractUserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Rico on 2014-11-22.
 */
public class SocialUserServiceImpl extends AbstractUserService implements SocialUserService {

    private Map<SocialProvider.SocialType, SocialService> socialServices;

    @Override
    public UserDetails resolveDetails(Authentication authentication) {
        Assert.notNull(authentication.getSocial());
        Account account = accountDao.findByUniques(null, authentication.getUsername(), null);
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new SecurityUser(account.getId(), authentication.getUsername(), authentication.getPassword(), authentication.getSocial(), authorities);
    }

    @Override
    public boolean isValidCredentials(Account entity, UserDetails user)  {
        SecurityUser securityUser = (SecurityUser) user;
        return socialServices.get(securityUser.getSocial()).createConnection(securityUser);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) {
        Account account = accountDao.findByUniques(null, userId, null);
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new SecurityUser(account.getId(), account.getName(), account.getPassword(), null, authorities);

    }

    @Override
    public void signOn(Registration registration, Locale locale) {
        Account account = new Account();
        account.setEmail(registration.getEmail());
        account.setConfig(new Account.Config.Builder().firstName(registration.getFirstName()).lastName(registration.getLastName()).build().toString());
        account.setName(registration.getUsername());
        account.setStatus(Account.Status.ACTIVE);
        account.setSalt(KeyGenerators.string().generateKey());
        account.setPassword(SecurityUser.OAUTH);

        accountDao.create(account);
    }

    @Required
    public void setSocialServices(Map<SocialProvider.SocialType, SocialService> socialServices) {
        this.socialServices = socialServices;
    }
}
