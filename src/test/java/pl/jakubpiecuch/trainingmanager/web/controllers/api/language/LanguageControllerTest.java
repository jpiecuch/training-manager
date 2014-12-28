package pl.jakubpiecuch.trainingmanager.web.controllers.api.language;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.BaseControllerTestCase;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class LanguageControllerTest extends BaseControllerTestCase {

    private static final String VALID_LANG = "valid";
    private static final String NOT_VALID_LANG = "not_valid";
    private static final List<String> LANGS = new ArrayList<String>() {
        {
            add(VALID_LANG);
        }
    };

    @Autowired
    private LanguageController languageController;

    @Override
    protected LanguageController getController() {
        return languageController;
    }

    @Before
    public void setUpChild() throws Exception {
        super.setUp();
        Mockito.when(versionService.languages()).thenReturn(LANGS);
        Mockito.when(versionService.language(VALID_LANG)).thenReturn(new HashMap<String, String>());
        Mockito.when(versionService.language(NOT_VALID_LANG)).thenThrow(NotFoundException.class);
    }

    @Test
    public void testLang() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_LANGUAGE_PATH, ApiVersionService.Version.v1)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testLangs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_LANGUAGE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, VALID_LANG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_LANGUAGE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, NOT_VALID_LANG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}