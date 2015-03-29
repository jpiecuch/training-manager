package pl.jakubpiecuch.trainingmanager.web.authentication;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.encoder.password.PasswordEncoder;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

import java.util.List;

public class LocalAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private AccountDao accountDao;

    @Override
    public Authentication authenticate(Authentication a) {
        Account account = accountDao.findByUniques(null, a.getName(), null);
        if (account == null) {
            throw new BadCredentialsException("Username not found.");
        }
        if (!((UserDetails)a.getPrincipal()).getPassword().equals(account.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new UsernamePasswordAuthenticationToken(new SecurityUser(account.getId(), account.getName(), account.getPassword(), null, authorities), account.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
