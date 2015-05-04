package pl.jakubpiecuch.trainingmanager.service.user.social;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import java.util.List;

public class SocialSignInAdapter implements SignInAdapter {

    private AccountDao accountDao;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        Account account = accountDao.findByUniques(null, String.format(SecurityUser.SOCIAL_USERNAME_FORMAT, connection.createData().getProviderId(), connection.createData().getProviderUserId()), null);
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;

        SecurityUser userDetails = new SecurityUser(account.getId(), account.getName(), SecurityUser.OAUTH, SocialProvider.SocialType.valueOf(StringUtils.upperCase(connection.getKey().getProviderId())), authorities);
        WebUtil.authenticate(userDetails);
        return "/";
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
