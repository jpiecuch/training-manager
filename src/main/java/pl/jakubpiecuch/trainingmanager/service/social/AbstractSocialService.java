package pl.jakubpiecuch.trainingmanager.service.social;


import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import pl.jakubpiecuch.trainingmanager.service.social.SocialService;

public abstract class AbstractSocialService<T> implements SocialService {
    private ConnectionRepository connectionRepository;
    protected String url;

    protected abstract Class<T> getConnectionClass();

    protected Connection<T> connection() {
        return connectionRepository.findPrimaryConnection(getConnectionClass());
    }

    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }
}
