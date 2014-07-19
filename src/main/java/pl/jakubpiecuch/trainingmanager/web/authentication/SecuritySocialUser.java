package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService.Social;


public class SecuritySocialUser extends SocialUser {
    
    private Social.Type type;
    
    public SecuritySocialUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Social.Type type) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.type = type;
    }

    public Social.Type getType() {
        return type;
    }

    public void setType(Social.Type type) {
        this.type = type;
    }
}
