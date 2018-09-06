package pl.jakubpiecuch.gymhome.service.user.social;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.service.repository.Repository;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;
import pl.jakubpiecuch.gymhome.web.util.WebUtil;

import java.util.List;

public class SocialSignInAdapter implements SignInAdapter {

    private Repository<Account, AccountCriteria> repository;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        Account account = findUser(String.format(SecurityUser.SOCIAL_USERNAME_FORMAT, connection.createData().getProviderId(), connection.createData().getProviderUserId()));
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;

        SecurityUser userDetails = new SecurityUser(account.getId(), account.getName(), SecurityUser.OAUTH, SocialProvider.SocialType.valueOf(StringUtils.upperCase(connection.getKey().getProviderId())), authorities);
        WebUtil.authenticate(userDetails);
        return "/";
    }

    private Account findUser(String username) {
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
