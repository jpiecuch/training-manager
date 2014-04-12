package pl.jakubpiecuch.trainingmanager.web.services;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.jakubpiecuch.trainingmanager.domain.Users;
import pl.jakubpiecuch.trainingmanager.web.authentication.SecurityUser;

public class AuthenticatedUserUtil {
    
    private static final String NOT_AUTHENTICAED = "anonymousUser";

    public static SecurityUser getAuthenticatedUserDetails() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAuthenticated() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals(NOT_AUTHENTICAED);
    }
    
    public static Users getUser() {
        return new Users(getAuthenticatedUserDetails().getId(), getAuthenticatedUserDetails().getCalendarId());
    }
}
