package pl.jakubpiecuch.gymhome.web.util;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;

public class AuthenticatedUserUtil {

    private AuthenticatedUserUtil() {
    }

    public static SecurityUser getAuthenticatedUserDetails() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return object instanceof String ? null : (SecurityUser) object;
    }

    public static Account getUser() {
        return new Account(getAuthenticatedUserDetails().getId());
    }
}
