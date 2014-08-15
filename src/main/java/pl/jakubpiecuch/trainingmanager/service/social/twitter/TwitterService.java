package pl.jakubpiecuch.trainingmanager.service.social.twitter;

import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import pl.jakubpiecuch.trainingmanager.service.social.AbstractSocialService;

public class TwitterService extends AbstractSocialService<Twitter> {

    @Override
    protected Class<Twitter> getConnectionClass() {
        return Twitter.class;
    }

    @Override
    public void publicMessage(String code) {
        throw new UnsupportedOperationException();
    }
}
