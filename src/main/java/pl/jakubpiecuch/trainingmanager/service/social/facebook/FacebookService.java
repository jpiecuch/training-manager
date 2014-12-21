package pl.jakubpiecuch.trainingmanager.service.social.facebook;

import org.springframework.social.facebook.api.Facebook;
import pl.jakubpiecuch.trainingmanager.service.social.AbstractSocialService;


public class FacebookService extends AbstractSocialService<Facebook> {
    @Override
    protected Class<Facebook> getConnectionClass() {
        return Facebook.class;
    }

    @Override
    public void publicMessage(String code) {
        connection().getApi().openGraphOperations().publishAction("perform", "workout", String.format(url, code));
    }

    @Override
    protected String getRestUrl() {
        return "https://graph.facebook.com/me?access_token=%s";
    }
}
