package pl.jakubpiecuch.trainingmanager.web.controllers.api.locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.BaseControllerTestCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocaleControllerTest extends BaseControllerTestCase {

    private static final String NOT_SUPPORTED_LOCALE = "de";
    private static final String SUPPORTED_LOCALE = "pl";

    @Before
    public void setUp() {
        super.setUp();
        Mockito.doThrow(IllegalArgumentException.class)
                .when(localeService)
                .update(Mockito.any(HttpServletRequest.class),
                        Mockito.any(HttpServletResponse.class),
                        Mockito.eq(StringUtils.parseLocaleString(NOT_SUPPORTED_LOCALE)));
    }

    @Test
    public void testPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_LOCALE_PATH, ApiVersionService.Version.v1)
                .content(SUPPORTED_LOCALE).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testPostNotSupportedLocale() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_LOCALE_PATH, ApiVersionService.Version.v1).content(NOT_SUPPORTED_LOCALE)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}