package pl.jakubpiecuch.trainingmanager.service.user.social;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jakubpiecuch.trainingmanager.service.user.local.LocalProvider;

/**
 * Created by Rico on 2014-11-22.
 */
public class SocialProvider extends LocalProvider {
    protected final static Logger LOGGER = LoggerFactory.getLogger(SocialProvider.class);

    public enum SocialType {
        FACEBOOK("facebook"), GOOGLE("google"), TWITTER("twitter");

        private String providerId;

        private SocialType(String providerId) {
            this.providerId = providerId;
        }

        public String getProviderId() {
            return providerId;
        }
    }

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
            LOGGER.error("Not matching social: " +  id, e);
            return null;
        }
    }

    @Override
    public Type getType() {
        return Type.SOCIAL;
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
