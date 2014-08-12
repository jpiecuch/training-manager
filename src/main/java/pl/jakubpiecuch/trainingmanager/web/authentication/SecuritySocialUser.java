package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService.Social;


public class SecuritySocialUser extends SocialUser {
    
    private Social.Type[] socials;
    
    public SecuritySocialUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Social.Type[] getSocials() {
        return socials;
    }

    public void setSocials(Social.Type[] socials) {
        this.socials = socials;
    }
}
