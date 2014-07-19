package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface AuthenticationService extends UserDetailsService {

    boolean availability(String field, String value);
    enum ResetStatus { OK,USER_NOT_EXIST }
    ResetStatus resetPassword(String emial);
    boolean create(Users user, Locale locale);
    void socialSignUp(WebRequest request, boolean authenticate);
    boolean activate(String value);
    List<Social> availableSocials();
    UserDetails loadUserByUsername(String username, Connection<?> connection) throws UsernameNotFoundException;
    
    public class Social {
        protected final static Logger LOGGER = LoggerFactory.getLogger(Social.class);
        
        public enum Type {
            FACEBOOK("facebook"), GOOGLE("google"), TWITTER("twitter");
            
            private String providerId;
            
            private Type(String providerId) {
                this.providerId = providerId;
            }
            
            public String getProvierId() {
                return providerId;
            }
        }
        
        private final String id;
        private final String scope;
        
        private Social(Builder builder) {
            this.id = builder.id;
            this.scope = builder.scope;
        }

        public String getId() {
            return id;
        }

        public String getScope() {
            return scope;
        }
        
        public Type getType() {
            try {
                return Type.valueOf(StringUtils.upperCase(id));
            } catch (IllegalArgumentException e) {
                LOGGER.error("Not matching social: {}", id);
            } finally {
                return null;
            }
        }
        
        public static class Builder {
            private String id;
            private String scope;
            
            public Builder id(String id) {this.id = id; return this;}
            public Builder scope(String scope) {this.scope = scope; return this;}
            
            public Social build() {
                return new Social(this);
            }
        }
    }
}
