package pl.jakubpiecuch.trainingmanager.service.social.google;

import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.moments.CreateActivity;
import pl.jakubpiecuch.trainingmanager.service.social.AbstractSocialService;

public class GoogleService extends AbstractSocialService<Google> {
    @Override
    public void publicMessage(String code) {
        connection().getApi().plusOperations().insertMoment(new CreateActivity(String.format(url, code)));
    }

    @Override
    protected Class<Google> getConnectionClass() {
        return Google.class;
    }

    @Override
    public String getRestUrl() {
        return "https://www.googleapis.com/oauth2/v1/userinfo?access_token=%s";
    }
}
