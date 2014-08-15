package pl.jakubpiecuch.trainingmanager.service.social.google;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.moments.CreateActivity;
import pl.jakubpiecuch.trainingmanager.service.social.AbstractSocialService;

public class GoogleService extends AbstractSocialService<Google> {
    private String url;

    @Override
    public void publicMessage(String code) {
        connection().getApi().plusOperations().insertMoment(new CreateActivity(String.format(url, code)));
    }

    @Override
    protected Class<Google> getConnectionClass() {
        return Google.class;
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }
}
