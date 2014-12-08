package pl.jakubpiecuch.trainingmanager.service.social;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.web.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSocialService<T> implements SocialService {
    private ConnectionRepository connectionRepository;
    private UsersConnectionRepository usersConnectionRepository;
    private ConnectionFactoryRegistry connectionFactoryRegistry;
    private UserService userService;
    private UsersDao usersDao;
    protected String url;
    private RestTemplate rest = new RestTemplate();

    protected abstract Class<T> getConnectionClass();
    protected abstract String getRestUrl();

    protected Connection<T> connection() {
        return connectionRepository.findPrimaryConnection(getConnectionClass());
    }

    @Override
    public boolean createConnection(SecurityUser user, WebRequest request, Response<Authentication> response) throws Exception{
        try {
            Map<String, String> res = rest.getForObject(String.format(getRestUrl(), user.getPassword()), HashMap.class);
            String id = res.get("id");
            ConnectionData connectionData = id != null && id.equals(user.getUsername()) ? new ConnectionData(user.getSocial().getProviderId(), res.get("id"), res.get("name"), res.get("link"), null, user.getPassword(), null, null, null) : null;

            if (!response.hasErrors() && connectionData != null) {
                ConnectionFactory cf = connectionFactoryRegistry.getConnectionFactory(user.getSocial().getProviderId());

                OAuth2Connection<?> con = (OAuth2Connection<?>)cf.createConnection(connectionData);
                List<String> userIDs = usersConnectionRepository.findUserIdsWithConnection(con);

                if (userIDs.size() == 1) {
                    String userId = userIDs.get(0);
                    ConnectionRepository conRep = usersConnectionRepository.createConnectionRepository(userId);
                    con = (OAuth2Connection<?>)conRep.getConnection(new ConnectionKey(user.getSocial().getProviderId(), user.getUsername()));
                    conRep.updateConnection(con);
                    response.addEntity(new Authentication(usersDao.findByUniques(null, String.format(SecurityUser.SOCIAL_USRENAME_FORMAT, user.getSocial().getProviderId(), user.getUsername()), null)));
                    return true;
                } else  if (userIDs.isEmpty()) {
                    ProviderSignInAttempt signInAttempt = new ProviderSignInAttempt(con, connectionFactoryRegistry, usersConnectionRepository);
                    request.setAttribute("org.springframework.social.connect.web.ProviderSignInAttempt", signInAttempt, RequestAttributes.SCOPE_SESSION);
                    NativeWebRequest nativeWebRequest = (NativeWebRequest) request;
                    HttpServletRequest req = (HttpServletRequest) nativeWebRequest.getNativeRequest();
                    Registration registration = new Registration();
                    Connection<?> connection = ProviderSignInUtils.getConnection(request);
                    UserProfile profile = connection.fetchUserProfile();
                    registration.setUsername(String.format("%s:%s", connection.getKey().getProviderId(), connection.getKey().getProviderUserId()));
                    registration.setEmail(profile.getEmail());
                    registration.setFirstName(profile.getFirstName());
                    registration.setLastName(profile.getLastName());
                    Response<Registration> registrationResponse = new Response<Registration>();
                    userService.signOn(registration, registrationResponse, request.getLocale());
                    response.addEntity(registrationResponse.getEntity());
                    ProviderSignInUtils.handlePostSignUp(registration.getUsername(), request);
                    return true;
                } else {
                    throw new RuntimeException("Multiple oauth connections for single user");
                }
            } else {
                response.addError(new Response.Error.Builder(Response.Error.Type.RUNTIME).value(Response.Error.Code.ACCOUNT_INVALID).build());
            }
        } catch(HttpClientErrorException e) {
            response.addError(new Response.Error.Builder(Response.Error.Type.RUNTIME).value(Response.Error.Code.ACCOUNT_INVALID).build());
        }
        return false;
    }

    @Autowired
    public void setConnectionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Autowired
    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }

    @Autowired
    public void setConnectionFactoryRegistry(ConnectionFactoryRegistry connectionFactoryRegistry) {
        this.connectionFactoryRegistry = connectionFactoryRegistry;
    }

    @Autowired
    @Qualifier("socialUserService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }
}
