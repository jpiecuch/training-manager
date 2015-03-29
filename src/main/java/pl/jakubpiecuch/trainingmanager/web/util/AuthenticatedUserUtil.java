package pl.jakubpiecuch.trainingmanager.web.util;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

import java.util.ArrayList;
import java.util.List;

public class AuthenticatedUserUtil {

    private AuthenticatedUserUtil() {
    }

    public static SecurityUser getAuthenticatedUserDetails() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return object instanceof String ? null : (SecurityUser) object;
    }

    public static List<String> getGrantedPermissions(Account account) {
        List<String> authorities = null;
        if (CollectionUtils.isNotEmpty(account.getRoles())) {
            authorities = new ArrayList<String>();
            for (Role role : account.getRoles()) {
                for (String permission : role.getGrantedPermissions()) {
                    authorities.add(permission);
                }
            }
        }
        return authorities;
    }
    
    public static Account getUser() {
        return new Account(getAuthenticatedUserDetails().getId());
    }
}
