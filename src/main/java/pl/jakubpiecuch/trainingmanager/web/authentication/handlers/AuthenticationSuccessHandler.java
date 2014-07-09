package pl.jakubpiecuch.trainingmanager.web.authentication.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private RequestCache requestCache;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            response.sendRedirect(requestCache.getRequest(request, response).getRedirectUrl());
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    @Autowired
    public void setRequestCache(RequestCache requestCache) {
        super.setRequestCache(requestCache); //To change body of generated methods, choose Tools | Templates.
    }
}
