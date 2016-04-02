package pl.jakubpiecuch.trainingmanager.web.controllers.api.resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.BaseControllerTestCase;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ResourceControllerTest extends BaseControllerTestCase {

    private static final String DIRECTORY_KEY = "directory";
    private static final String NOT_EXISTS_DIRECTORY_KEY = "not_exists_directory";
    private static final ResourceService.Type TYPE = ResourceService.Type.image;
    private static final String FIRST = "first.jpg";
    private static final String SECOND = "second.jpg";
    private static final String THIRD = "third.jpg";
    private static final List<String> LIST = new ArrayList<String>() {
        {
            add(FIRST);
            add(SECOND);
            add(THIRD);
        }
    };
    private static final String FILE_KEY = "file";

    @Before
    public void setUpChild() throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        super.setUp();
        Mockito.when(versionService.resources(TYPE).resource(DIRECTORY_KEY)).thenReturn(new ResponseEntity(LIST, HttpStatus.OK));
        Mockito.when(versionService.resources(TYPE).resource(FILE_KEY)).thenReturn(new ResponseEntity(FIRST.getBytes(), headers, HttpStatus.OK));
        Mockito.when(versionService.resources(TYPE).resource(NOT_EXISTS_DIRECTORY_KEY)).thenThrow(NotFoundException.class);
    }

    @Test
    public void testGET() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_RESOURCE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, TYPE, FILE_KEY)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_RESOURCE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, TYPE, DIRECTORY_KEY)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_RESOURCE_PATH + ApiURI.KEY_PATH_PARAM, ApiVersionService.Version.v1, TYPE, NOT_EXISTS_DIRECTORY_KEY)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}