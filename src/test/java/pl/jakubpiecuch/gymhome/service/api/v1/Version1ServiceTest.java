package pl.jakubpiecuch.gymhome.service.api.v1;

import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.StringUtils;
import pl.jakubpiecuch.gymhome.service.locale.LocaleService;
import pl.jakubpiecuch.gymhome.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;
import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Version1ServiceTest {

    private static final String LANG_EN = "en";
    private static final String LANG_PL = "pl";
    private static final String[] LANGS = new String[]{LANG_EN, LANG_PL};
    private static final String NOT_VALID_LOCALE = "en";
    private static final String NOT_VALID_USERNAME = "not valid username";
    private static final String NOT_VALID_PASSWORD = "not valid password";
    private static final String NOT_PERSISTED_USER_USERNAME = "not persisted username";
    private static final String NOT_PERSISTED_USER_PASSWORD = "not persisted password";
    private static Authentication NOT_FOUND_SOCIAL_USER_AUTHENTICATION = new Authentication();
    private static Authentication NOT_PERSISTED_USER_AUTHENTICATION = new Authentication();
    private static Authentication NOT_VALID_AUTHENTICATION = new Authentication();
    @InjectMocks
    private Version1Service service;

    @Mock
    private LocaleService localeService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private TranslatesService translatesService;


    @Before
    public void setUp() {
        NOT_VALID_AUTHENTICATION.setUsername(NOT_VALID_USERNAME);
        NOT_VALID_AUTHENTICATION.setPassword(NOT_VALID_PASSWORD);

        NOT_PERSISTED_USER_AUTHENTICATION.setUsername(NOT_PERSISTED_USER_USERNAME);
        NOT_PERSISTED_USER_AUTHENTICATION.setPassword(NOT_PERSISTED_USER_PASSWORD);

        NOT_FOUND_SOCIAL_USER_AUTHENTICATION.setUsername(NOT_VALID_USERNAME);
        NOT_FOUND_SOCIAL_USER_AUTHENTICATION.setPassword(NOT_PERSISTED_USER_PASSWORD);

        Mockito.when(translatesService.languages()).thenReturn(Arrays.asList(LANGS));
    }

    @Test
    public void testLanguages() throws Exception {
        List<String> languages = service.translates().languages();
        assertEquals(2, languages.size());
        assertEquals(LANG_EN, languages.get(0));
        assertEquals(LANG_PL, languages.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocaleNullLocale() throws Exception {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        Mockito.doThrow(IllegalArgumentException.class).when(localeService).update(httpServletRequest, httpServletResponse, null);
        service.locale().update(httpServletRequest, httpServletResponse, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocaleNotValidLocale() throws Exception {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        Mockito.doThrow(IllegalArgumentException.class).when(localeService).update(httpServletRequest, httpServletResponse, StringUtils.parseLocaleString(NOT_VALID_LOCALE));
        service.locale().update(httpServletRequest, httpServletResponse, StringUtils.parseLocaleString(NOT_VALID_LOCALE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignInWithNullAuthentication() throws Exception {
        Mockito.doThrow(IllegalArgumentException.class).when(authenticationService).signIn(request, response, null);
        service.authentication().signIn(request, response, null);
    }

    @Test(expected = ValidationException.class)
    public void testSignInWithNotValidAuthentication() throws Exception {
        Mockito.doThrow(ValidationException.class).when(authenticationService).signIn(request, response, NOT_VALID_AUTHENTICATION);
        service.authentication().signIn(request, response, NOT_VALID_AUTHENTICATION);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testSignInWithNotPersistedUser() throws Exception {
        Mockito.doThrow(ObjectNotFoundException.class).when(authenticationService).signIn(request, response, NOT_PERSISTED_USER_AUTHENTICATION);
        service.authentication().signIn(request, response, NOT_PERSISTED_USER_AUTHENTICATION);
    }

    @Test(expected = NotFoundException.class)
    public void testSignInWithNotFoundSocialUser() throws Exception {
        Mockito.doThrow(NotFoundException.class).when(authenticationService).signIn(request, response, NOT_FOUND_SOCIAL_USER_AUTHENTICATION);
        service.authentication().signIn(request, response, NOT_FOUND_SOCIAL_USER_AUTHENTICATION);
    }

    @Test
    public void testSignOut() throws Exception {

    }

    @Test
    public void testSignOn() throws Exception {

    }

    @Test
    public void testSigned() throws Exception {

    }

    @Test
    public void testResource() throws Exception {

    }

    @Test
    public void testLanguage() throws Exception {

    }
}