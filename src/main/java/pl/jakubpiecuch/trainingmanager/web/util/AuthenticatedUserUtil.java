package pl.jakubpiecuch.trainingmanager.web.util;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

public class AuthenticatedUserUtil {
    
    private static final String NOT_AUTHENTICATED = "anonymousUser";

    public static SecurityUser getAuthenticatedUserDetails() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return object instanceof String ? null : (SecurityUser) object;
    }

    public static boolean isAuthenticated() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals(NOT_AUTHENTICATED);
    }
    
    public static Account getUser() {
        return new Account(getAuthenticatedUserDetails().getId());
    }
}
