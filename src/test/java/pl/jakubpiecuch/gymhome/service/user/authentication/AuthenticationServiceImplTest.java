package pl.jakubpiecuch.gymhome.service.user.authentication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.user.UserService;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {


    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String NAME = "authentication";
    private static Authentication NULL_SOCIAL_AUTHENTICATION = new Authentication();
    private static Authentication NULL_PROVIDER_AUTHENTICATION = new Authentication();
    private static Authentication NOT_VALID_AUTHENTICATION = new Authentication();
    private static Errors BINDING_RESULTS = new BeanPropertyBindingResult(NOT_VALID_AUTHENTICATION, NAME);
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private RepoDao<Account, AccountCriteria> accountDao;

    @Mock
    private Validator validator;

    @Mock
    private UserService localUserService;

    @Mock
    private UserService socialUserService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private Map<Provider.Type, UserService> userServices;

    @Before
    public void setUp() throws Exception {
        NULL_PROVIDER_AUTHENTICATION.setPassword(PASSWORD);
        NULL_PROVIDER_AUTHENTICATION.setUsername(USERNAME);

        NULL_SOCIAL_AUTHENTICATION.setPassword(PASSWORD);
        NULL_SOCIAL_AUTHENTICATION.setUsername(USERNAME);
        NULL_SOCIAL_AUTHENTICATION.setProvider(Provider.Type.SOCIAL);

        userServices = new HashMap<Provider.Type, UserService>() {
            {
                put(Provider.Type.LOCAL, localUserService);
                put(Provider.Type.SOCIAL, socialUserService);
            }
        };
        authenticationService.setUserServices(userServices);
        Mockito.doThrow(ValidationException.class).when(validator).validate(NOT_VALID_AUTHENTICATION, BINDING_RESULTS);
        Mockito.when(socialUserService.resolveDetails(NULL_SOCIAL_AUTHENTICATION)).thenThrow(IllegalArgumentException.class);
    }

    @Test(expected = ValidationException.class)
    public void testSignInNotValidAuthentication() throws Exception {
        authenticationService.signIn(request, response, NOT_VALID_AUTHENTICATION);
    }

    @Test(expected = NullPointerException.class)
    public void testSignInNullProvider() throws Exception {
        authenticationService.signIn(request, response, NULL_PROVIDER_AUTHENTICATION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignInNullSocial() throws Exception {
        authenticationService.signIn(request, response, NULL_SOCIAL_AUTHENTICATION);
    }

}