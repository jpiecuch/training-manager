package pl.jakubpiecuch.trainingmanager.web.controllers.api.plan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.BaseControllerTestCase;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import static org.junit.Assert.*;

/**
 * Created by jakub on 12.12.2015.
 */
public class PlanControllerTest extends BaseControllerTestCase {

    private static final long PLAN_ID = 1l;
    private static final long USED_ID = 2l;
    private static final long NOT_EXISTS_ID = 3l;

    @Before
    public void setUpChild() {
        super.setUp();
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.doThrow(IllegalArgumentException.class).when(versionService).removeFromRepository(USED_ID, Repositories.PLAN);

        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, USED_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.doThrow(NotFoundException.class).when(versionService).removeFromRepository(NOT_EXISTS_ID, Repositories.PLAN);

        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, NOT_EXISTS_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}