package pl.jakubpiecuch.gymhome.service.user.social;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jakubpiecuch.gymhome.service.user.local.LocalProvider;

/**
 * Created by Rico on 2014-11-22.
 */
public class SocialProvider extends LocalProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocialProvider.class);
    private final String scope;

    private SocialProvider(Builder builder) {
        super(builder.id);
        this.scope = builder.scope;
    }

    public String getScope() {
        return scope;
    }

    public SocialType getSocialType() {
        try {
            return SocialType.valueOf(StringUtils.upperCase(id));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Not matching social: " + id, e);
            return null;
        }
    }

    @Override
    public Type getType() {
        return Type.SOCIAL;
    }

    public enum SocialType {
        FACEBOOK("facebook"), GOOGLE("google"), TWITTER("twitter"), NONE("none");

        private String providerId;

        SocialType(String providerId) {
            this.providerId = providerId;
        }

        public String getProviderId() {
            return providerId;
        }
    }

    public static class Builder {
        private String id;
        private String scope;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public SocialProvider build() {
            return new SocialProvider(this);
        }
    }
}
