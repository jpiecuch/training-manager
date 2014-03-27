package pl.jakubpiecuch.trainingmanager.web.services;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jakubpiecuch.trainingmanager.web.authentication.SecurityUser;

public class AuthenticatedUserUtil {

    public static SecurityUser getAuthenticatedUserDetails() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean correctPassword(String password) {
        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
        return passwordEncoder.encodePassword(password, getAuthenticatedUserDetails().getSalt()).equals(getAuthenticatedUserDetails().getPassword());
    }

    public static boolean isAuthenticated() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser");
    }
}
