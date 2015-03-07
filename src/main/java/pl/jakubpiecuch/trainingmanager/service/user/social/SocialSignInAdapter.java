package pl.jakubpiecuch.trainingmanager.service.user.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

public class SocialSignInAdapter implements SignInAdapter {

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        SecurityUser userDetails = new SecurityUser(null, connection.createData().getProviderUserId(), SecurityUser.OAUTH, SocialProvider.SocialType.valueOf(StringUtils.upperCase(connection.getKey().getProviderId())));
        WebUtil.authenticate(userDetails);
        return "/";
    }
}
