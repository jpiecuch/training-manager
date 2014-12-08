package pl.jakubpiecuch.trainingmanager.service.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends SocialUser {

    public static final String SOCIAL_USER_DELIMITER = ":";
    public static final String SOCIAL_USRENAME_FORMAT = "%s" + SOCIAL_USER_DELIMITER + "%s";

    private Long id;
    private SocialProvider.SocialType social;
    private String salt;

    public SecurityUser(Long id, String username, String password, SocialProvider.SocialType social, String salt) {
        super(username, password, new ArrayList<GrantedAuthority>());
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
