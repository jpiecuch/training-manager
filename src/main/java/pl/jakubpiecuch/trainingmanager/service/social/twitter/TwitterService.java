package pl.jakubpiecuch.trainingmanager.service.social.twitter;

import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import pl.jakubpiecuch.trainingmanager.service.social.AbstractSocialService;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;

public class TwitterService extends AbstractSocialService<Twitter> {

    @Override
    protected Class<Twitter> getConnectionClass() {
        return Twitter.class;
    }

    @Override
    public void publicMessage(String code) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getRestUrl() {
        return "https://api.twitter.com/oauth/authenticate?oauth_token=%s";
    }
}
