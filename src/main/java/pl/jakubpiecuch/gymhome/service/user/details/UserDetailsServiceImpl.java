package pl.jakubpiecuch.gymhome.service.user.details;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.service.repository.Repository;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.user.local.assertion.AccountAssert;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;

import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private Repository<Account, AccountCriteria> repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = findUser(username);
        AccountAssert.isTrue(account != null && Account.Status.ACTIVE == account.getStatus());
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new SecurityUser(account.getId(), account.getName(), account.getCredential(), null, authorities);
    }

    protected Account findUser(String username) {
        PageResult<Account> result = repository.page(new AccountCriteria().addNameRestrictions(username));
        if (result.getCount() > 0) {
            return result.getResult().get(0);
        }
        return null;
    }

    public void setRepository(Repository<Account, AccountCriteria> repository) {
        this.repository = repository;
    }
}
