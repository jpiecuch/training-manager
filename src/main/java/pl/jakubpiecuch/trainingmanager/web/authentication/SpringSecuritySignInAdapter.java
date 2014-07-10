package pl.jakubpiecuch.trainingmanager.web.authentication;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

public class SpringSecuritySignInAdapter implements SignInAdapter {
    
    protected final static Logger LOGGER = LoggerFactory.getLogger(SpringSecuritySignInAdapter.class);
    
    private AuthenticationService authenticationService;
    private RequestCache requestCache;
    
    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        UserDetails userDetails = authenticationService.loadUserByUsername(userId);
        WebUtil.authenticate(userDetails);
        
        SavedRequest savedRequest = requestCache.getRequest((HttpServletRequest) request.getNativeRequest(), null);
        
        return savedRequest != null ? savedRequest.getRedirectUrl() : null;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
