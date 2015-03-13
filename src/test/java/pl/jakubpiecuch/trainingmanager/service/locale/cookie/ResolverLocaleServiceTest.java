package pl.jakubpiecuch.trainingmanager.service.locale.cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class ResolverLocaleServiceTest {

    @InjectMocks
    private ResolverLocaleService service ;

    @Mock
    private LocaleResolver localeResolver;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    private final static String[] supported = new String[] {"pl","en"};
    private final static Locale LOCALE = new Locale("en");

    @Before
    public void setUp() {
        service.setSupportedLocales(supported);
        Mockito.doThrow(new IllegalArgumentException()).when(localeResolver).setLocale(null, response, LOCALE);
        Mockito.doThrow(new IllegalArgumentException()).when(localeResolver).setLocale(request, null, LOCALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithNullRequest() {
        service.update(null, response, LOCALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithNullResponse() {
        service.update(request, null, LOCALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithNullLocale() {
        service.update(request, response, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithNotSuppoertedLocale() {
        service.update(request, response, new Locale("de"));
    }

    @Test
    public void testUpdate()  {
        service.update(request, response, LOCALE);
    }
}