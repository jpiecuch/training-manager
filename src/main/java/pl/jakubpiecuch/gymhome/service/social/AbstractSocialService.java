package pl.jakubpiecuch.gymhome.service.social;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.gymhome.service.user.UserService;
import pl.jakubpiecuch.gymhome.service.user.model.Registration;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;
import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSocialService<T> implements SocialService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSocialService.class);
    protected String url;
    protected WebRequest request;
    private ConnectionRepository connectionRepository;
    private UsersConnectionRepository usersConnectionRepository;
    private ConnectionFactoryRegistry connectionFactoryRegistry;
    private UserService userService;
    private RestTemplate rest = new RestTemplate();
    private ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();

    protected abstract Class<T> getConnectionClass();

    protected abstract String getRestUrl();

    protected Connection<T> connection() {
        return connectionRepository.findPrimaryConnection(getConnectionClass());
    }

    @Override
    public boolean createConnection(SecurityUser user) {
        try {

            ConnectionData connectionData = connectionData(user, rest.getForObject(String.format(getRestUrl(), user.getPassword()), HashMap.class));

            if (connectionData != null) {
                ConnectionFactory cf = connectionFactoryRegistry.getConnectionFactory(user.getSocial().getProviderId());

                OAuth2Connection<?> con = (OAuth2Connection<?>) cf.createConnection(connectionData);
                List<String> userIDs = usersConnectionRepository.findUserIdsWithConnection(con);

                if (CollectionUtils.isEmpty(userIDs)) {
                    return createFromNotExistsingUser(con);
                } else if (userIDs.size() == 1) {
                    return createFromExistingUser(user, userIDs);
                }
                throw new IllegalArgumentException("Multiple oauth connections for single user");
            }
        } catch (HttpClientErrorException e) {
            LOGGER.warn("", e);
        }
        throw new NotFoundException();
    }

    private ConnectionData connectionData(SecurityUser user, Map<String, String> social) {
        String id = social.get("id");
        return id != null && id.equals(user.getUsername()) ? new ConnectionData(user.getSocial().getProviderId(), social.get("id"), social.get("name"), social.get("link"), null, user.getPassword(), null, null, null) : null;
    }

    private boolean createFromNotExistsingUser(OAuth2Connection<?> con) {
        ProviderSignInAttempt signInAttempt = new ProviderSignInAttempt(con, connectionFactoryRegistry, usersConnectionRepository);
        request.setAttribute("org.springframework.social.connect.web.ProviderSignInAttempt", signInAttempt, RequestAttributes.SCOPE_SESSION);
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        UserProfile profile = connection.fetchUserProfile();

        Registration registration = new Registration();
        registration.setUsername(String.format("%s:%s", connection.getKey().getProviderId(), connection.getKey().getProviderUserId()));
        registration.setEmail(profile.getEmail());
        registration.setFirstName(profile.getFirstName());
        registration.setLastName(profile.getLastName());

        userService.signOn(registration, request.getLocale());
        providerSignInUtils.doPostSignUp(registration.getUsername(), request);
        return true;
    }

    private boolean createFromExistingUser(SecurityUser user, List<String> ids) {
        OAuth2Connection<?> con;
        String userId = ids.get(0);
        ConnectionRepository conRep = usersConnectionRepository.createConnectionRepository(userId);
        con = (OAuth2Connection<?>) conRep.getConnection(new ConnectionKey(user.getSocial().getProviderId(), user.getUsername()));
        conRep.updateConnection(con);
        return true;
    }


    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }

    public void setConnectionFactoryRegistry(ConnectionFactoryRegistry connectionFactoryRegistry) {
        this.connectionFactoryRegistry = connectionFactoryRegistry;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRequest(WebRequest request) {
        this.request = request;
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }

}
