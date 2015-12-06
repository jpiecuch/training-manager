package pl.jakubpiecuch.trainingmanager.service.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.Collection;

public class SecurityUser extends SocialUser {

    public static final String SOCIAL_USER_DELIMITER = ":";
    public static final String SOCIAL_USERNAME_FORMAT = "%s" + SOCIAL_USER_DELIMITER + "%s";
    public static final String OAUTH = "oauth";

    private Long id;
    private SocialProvider.SocialType social;

    public SecurityUser(Long id, String username, String password, SocialProvider.SocialType social, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.social = social;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SocialProvider.SocialType getSocial() {
        return social;
    }

    public void setSocial(SocialProvider.SocialType social) {
        this.social = social;
    }
}
