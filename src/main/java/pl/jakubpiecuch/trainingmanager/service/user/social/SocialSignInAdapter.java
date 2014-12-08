package pl.jakubpiecuch.trainingmanager.service.user.social;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.Response;

public class SocialSignInAdapter implements SignInAdapter {
    
    protected final static Logger LOGGER = LoggerFactory.getLogger(SocialSignInAdapter.class);
    
    private SocialUserService userService;
    private RequestCache requestCache;
    
    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        SecurityUser userDetails = new SecurityUser(null, userId, null, SocialProvider.SocialType.valueOf(StringUtils.upperCase(connection.getKey().getProviderId())), null);

        try {
            userService.signIn(userDetails, request, new Response<Authentication>());
        } catch(Exception e) {

        }
        
        SavedRequest savedRequest = requestCache.getRequest((HttpServletRequest) request.getNativeRequest(), null);
        
        return savedRequest != null ? savedRequest.getRedirectUrl() : null;
    }

    @Autowired
    public void setUserService(SocialUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
