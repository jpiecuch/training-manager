package pl.jakubpiecuch.trainingmanager.service.social.facebook;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceTest {

    private static final String FACEBOOK_ID = "1234";
    private static final String FACENOOK_KEY = "abcd4321";
    private static final HashMap<String, String> FACEBOOK_RESPONSE = new HashMap<String, String>() {
        {
            put("id", FACEBOOK_ID);
        }
    };
    private static SecurityUser VALID_USER;

    @InjectMocks
    private FacebookService facebookService;

    @Mock
    private RestTemplate restTemplate;


    @Before
    public void setUp() {
        VALID_USER = new SecurityUser(null,FACEBOOK_ID, FACENOOK_KEY, SocialProvider.SocialType.FACEBOOK, null);
        Mockito.when(restTemplate.getForObject(String.format(facebookService.getRestUrl(), FACENOOK_KEY), HashMap.class)).thenReturn(FACEBOOK_RESPONSE);
    }


    @Test(expected = NullPointerException.class)
    public void testCreateConnectionWithNullUser() throws Exception {
        facebookService.createConnection(null);
    }

    @Test(expected = IllegalArgumentException .class)
    public void testCreateConnectionWithEmptyUser() throws Exception {
        facebookService.createConnection(new SecurityUser(null, "", "", SocialProvider.SocialType.FACEBOOK, ""));
    }

    @Test
    public void testCreateConnection() throws Exception{
        facebookService.createConnection(VALID_USER);
    }

}