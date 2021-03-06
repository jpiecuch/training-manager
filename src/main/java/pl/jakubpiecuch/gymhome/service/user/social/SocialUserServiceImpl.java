package pl.jakubpiecuch.gymhome.service.user.social;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.Assert;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.Role;
import pl.jakubpiecuch.gymhome.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.gymhome.service.social.SocialService;
import pl.jakubpiecuch.gymhome.service.user.AbstractUserService;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.model.Registration;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;

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
        Account account = findUser(authentication.getUsername());
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new SecurityUser(account.getId(), authentication.getUsername(), authentication.getPassword(), authentication.getSocial(), authorities);
    }

    @Override
    public boolean isValidCredentials(Account entity, UserDetails user) {
        SecurityUser securityUser = (SecurityUser) user;
        return socialServices.get(securityUser.getSocial()).createConnection(securityUser);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) {
        Account account = findUser(userId);
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new SecurityUser(account.getId(), account.getName(), account.getCredential(), null, authorities);

    }

    @Override
    public void signOn(Registration registration, Locale locale) {
        Account account = new Account();
        account.setEmail(registration.getEmail());
        account.setConfig(new Account.Config.Builder().firstName(registration.getFirstName()).lastName(registration.getLastName()).build().toString());
        account.setName(registration.getUsername());
        account.setStatus(Account.Status.ACTIVE);
        account.setSalt(KeyGenerators.string().generateKey());
        account.setCredential(SecurityUser.OAUTH);
        account.setProvider(Provider.Type.SOCIAL);
        account.setSocialType(registration.getSocial());
        account.getRoles().addAll(roleRepository.page(new RoleCriteria().addNameRestrictions(Role.USER_ROLE)).getResult());

        repository.create(account);
    }

    @Required
    public void setSocialServices(Map<SocialProvider.SocialType, SocialService> socialServices) {
        this.socialServices = socialServices;
    }
}
