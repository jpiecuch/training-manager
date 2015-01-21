package pl.jakubpiecuch.trainingmanager.service.social.facebook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.service.user.UserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceTest {

    private static final String FACEBOOK_ID = "1234";
    private static final String FACEBOOK_USER_ID = SocialProvider.SocialType.FACEBOOK.getProviderId() + ":" + FACEBOOK_ID;
    private static final String FACEBOOK_KEY = "abcd4321";
    private static final HashMap<String, String> FACEBOOK_RESPONSE = new HashMap<String, String>() {
        {
            put("id", FACEBOOK_ID);
        }
    };
    private static final List<String> NOT_EMPTY_CON_REP_RESPONSE = new ArrayList<String>() {
        {
            add(FACEBOOK_USER_ID);
        }
    };

    private static final List<String> MULTI_VALUE_CON_REP_RESPONSE = new ArrayList<String>() {
        {
            add(FACEBOOK_USER_ID);
            add("5678");
        }
    };

    private static final List<String> EMPTY_CON_REP_RESPONSE = new ArrayList<String>();

    private static SecurityUser VALID_USER;

    private static final String FACEBOOK_FIRST_NAME = "firstName";
    private static final String FACEBOOK_LAST_NAME = "lastName";
    private static final String FACEBOOK_EMAIL = "email";
    private static final String FACEBOOK_USERNAME = "username";
    private static final UserProfile USER_PROFILE = new UserProfileBuilder().setName(FACEBOOK_ID).setFirstName(FACEBOOK_FIRST_NAME)
    .setLastName(FACEBOOK_LAST_NAME).setEmail(FACEBOOK_EMAIL).setUsername(FACEBOOK_USERNAME).build();

    private static final String PROVIDER_ID = "facebook";
    private static final String PROVIDER_USER_ID = FACEBOOK_ID;

    @InjectMocks
    private FacebookService facebookService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ConnectionFactoryRegistry connectionFactoryRegistry;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private OAuth2Connection<?> oAuth2Connection;

    @Mock
    private UsersConnectionRepository usersConnectionRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private WebRequest request;

    @Mock
    private ProviderSignInUtils providerSignInUtils;

    @Mock
    private Connection connection;

    private ConnectionKey connectionKey = new ConnectionKey(PROVIDER_ID, PROVIDER_USER_ID);

    @Mock
    private UserService userService;


    @Before
    public void setUp() {
        VALID_USER = new SecurityUser(null,FACEBOOK_ID, FACEBOOK_KEY, SocialProvider.SocialType.FACEBOOK, null);
        Mockito.when(restTemplate.getForObject(String.format(facebookService.getRestUrl(), FACEBOOK_KEY), HashMap.class)).thenReturn(FACEBOOK_RESPONSE);
        Mockito.when(connectionFactoryRegistry.getConnectionFactory(SocialProvider.SocialType.FACEBOOK.getProviderId())).thenReturn(connectionFactory);
        Mockito.when(connectionFactory.createConnection(Matchers.any(ConnectionData.class))).thenReturn(oAuth2Connection);
    }


    @Test(expected = NullPointerException.class)
    public void testCreateConnectionWithNullUser() throws Exception {
        facebookService.createConnection(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConnectionWithEmptyUser() throws Exception {
        facebookService.createConnection(new SecurityUser(null, "", "", SocialProvider.SocialType.FACEBOOK, ""));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateConnectionWithToManyOAuthConn() throws Exception {
        Mockito.when(usersConnectionRepository.findUserIdsWithConnection(oAuth2Connection)).thenReturn(MULTI_VALUE_CON_REP_RESPONSE);

        facebookService.createConnection(VALID_USER);
    }

    @Test
    public void testCreateConnectionWithPersistedUser() throws Exception{
        Mockito.when(usersConnectionRepository.findUserIdsWithConnection(oAuth2Connection)).thenReturn(NOT_EMPTY_CON_REP_RESPONSE);
        Mockito.when(usersConnectionRepository.createConnectionRepository(FACEBOOK_USER_ID)).thenReturn(connectionRepository);

        Assert.assertTrue(facebookService.createConnection(VALID_USER));
    }

    @Test
    public void testCreateConnectionWithNotPersistedUser() throws Exception{
        Mockito.when(usersConnectionRepository.findUserIdsWithConnection(oAuth2Connection)).thenReturn(EMPTY_CON_REP_RESPONSE);
        Mockito.when(providerSignInUtils.getConnectionFromSession(request)).thenReturn(connection);
        Mockito.when(connection.fetchUserProfile()).thenReturn(USER_PROFILE);
        Mockito.when(connection.getKey()).thenReturn(connectionKey);
        Assert.assertTrue(facebookService.createConnection(VALID_USER));
    }



}