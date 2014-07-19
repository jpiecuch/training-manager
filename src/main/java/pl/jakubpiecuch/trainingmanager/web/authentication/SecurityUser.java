package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService.Social;

public class SecurityUser extends SecuritySocialUser {
    
    private Long id;
    private String salt;
    private String fullName;
    private Long calendarId;

    public SecurityUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, String salt, String fullName, Long calendarId, Social.Type type) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, type);
        this.salt = salt;
        this.fullName = fullName;
        this.id = id;
        this.calendarId = calendarId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Long getCalendarId() {
        return this.calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
