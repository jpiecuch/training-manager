package pl.jakubpiecuch.trainingmanager.web.controllers.api.language;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.BaseControllerTestCase;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LanguageControllerTest extends BaseControllerTestCase {

    private static final String VALID_LANG = "valid";
    private static final String NOT_VALID_LANG = "not_valid";
    private static final List<String> LANGS = new ArrayList<String>() {
        {
            add(VALID_LANG);
        }
    };

    @Before
    public void setUpChild() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldReturnLang() throws Exception {
        Mockito.when(versionService.translates().languages()).thenReturn(LANGS);

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_LANGUAGE_PATH, ApiVersionService.Version.v1)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTranslates() throws Exception {
        Mockito.when(versionService.translates().translates(VALID_LANG)).thenReturn(new HashMap<>());

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_LANGUAGE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, VALID_LANG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotReturnTranslatesBecauseWrongKey() throws Exception {
        Mockito.when(versionService.translates().translates(NOT_VALID_LANG)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_LANGUAGE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, NOT_VALID_LANG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
