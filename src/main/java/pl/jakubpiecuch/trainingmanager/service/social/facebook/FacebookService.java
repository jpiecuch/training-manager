package pl.jakubpiecuch.trainingmanager.service.social.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;

import java.util.Map;


public class FacebookService implements SocialService {
    
    private ConnectionRepository connectionRepository;
    private String url;

    @Override
    public void post(Map<String, String> params) {
        Connection<Facebook> facebook = connectionRepository.findPrimaryConnection(Facebook.class);
        facebook.getApi().openGraphOperations().publishAction("perform", "workout", String.format(url, params.get(Params.CODE)));
    }

    @Autowired
    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }
}
