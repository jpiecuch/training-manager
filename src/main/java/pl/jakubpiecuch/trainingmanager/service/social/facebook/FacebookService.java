package pl.jakubpiecuch.trainingmanager.service.social.facebook;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.facebook.api.Facebook;

import java.util.Map;


public class FacebookService extends AbstractSocialService<Facebook> {
    private String url;

    @Override
    protected Class<Facebook> getConnectionClass() {
        return Facebook.class;
    }

    @Override
    public void post(Map<String, String> params) {
        connection().getApi().openGraphOperations().publishAction("perform", "workout", String.format(url, params.get(Params.CODE)));
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }
}
