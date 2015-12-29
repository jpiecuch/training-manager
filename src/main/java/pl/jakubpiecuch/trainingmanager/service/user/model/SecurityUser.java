package pl.jakubpiecuch.trainingmanager.service.user.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SecurityUser rhs = (SecurityUser) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.id, rhs.id)
                .append(this.social, rhs.social)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(id)
                .append(social)
                .toHashCode();
    }
}
