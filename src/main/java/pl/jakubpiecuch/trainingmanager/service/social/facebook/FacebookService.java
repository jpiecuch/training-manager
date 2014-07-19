package pl.jakubpiecuch.trainingmanager.service.social.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;


public class FacebookService implements SocialService {
    
    ConnectionRepository connectionRepository;

    @Override
    public void post(Message message) {
       throw new UnsupportedOperationException();
    }

    @Autowired
    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }
}
