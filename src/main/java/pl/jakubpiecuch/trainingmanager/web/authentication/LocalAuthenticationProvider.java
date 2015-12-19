package pl.jakubpiecuch.trainingmanager.web.authentication;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

import java.util.List;

public class LocalAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private ReadRepository<Account, AccountCriteria> repository;

    @Override
    public Authentication authenticate(Authentication a) {
        PageResult<Account> result = repository.read(new AccountCriteria().addNameRestrictions(a.getName()));
        if (result.getCount() == 0) {
            throw new BadCredentialsException("Username not found.");
        }
        Account account = result.getResult().get(0);
        if (!((UserDetails) a.getPrincipal()).getPassword().equals(account.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new UsernamePasswordAuthenticationToken(new SecurityUser(account.getId(), account.getName(), account.getPassword(), null, authorities), account.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    public void setRepository(ReadRepository<Account, AccountCriteria> repository) {
        this.repository = repository;
    }
}
