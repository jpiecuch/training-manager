package pl.jakubpiecuch.gymhome.web.controllers.api.plan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jakubpiecuch.gymhome.domain.Permissions;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanDto;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;
import pl.jakubpiecuch.gymhome.web.controllers.api.BaseControllerTestCase;
import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;

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

    //DELETE
    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_DELETER})
    public void shouldDeletePlan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_DELETER})
    public void shouldNotDeletePlanBecauseIsInUse() throws Exception {
        Mockito.doThrow(IllegalArgumentException.class).when(planRepository).delete(USED_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, USED_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_DELETER})
    public void shouldNotDeletePlanBecauseNotExists() throws Exception {
        Mockito.doThrow(NotFoundException.class).when(planRepository).delete(NOT_EXISTS_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, NOT_EXISTS_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @WithMockUser(username = "user")
    public void shouldNotDeletePlanBecauseNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    public void shouldNotDeletePlanBecauseNotSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    //POST
    @Test
    public void shouldNotCreatePlanBecauseNotSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_PLAN_PATH, ApiVersionService.Version.v1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "user")
    public void shouldNotCreatePlanBecauseNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_PLAN_PATH, ApiVersionService.Version.v1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_CREATOR})
    public void shouldCreatePlan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_PLAN_PATH, ApiVersionService.Version.v1)
                .content(OBJECT_MAPPER.writeValueAsString(new PlanDto())).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //GET LIST
    @Test
    @WithMockUser(username = "user")
    public void shouldNotGetPlansBecauseNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH, ApiVersionService.Version.v1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    public void shouldNotGetPlansBecauseNotSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH, ApiVersionService.Version.v1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_VIEWER})
    public void shouldGetPlans() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH, ApiVersionService.Version.v1).param("firstResult", "0")
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //GET ONE
    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_VIEWER})
    public void shouldGetPlan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {Permissions.PLAN_VIEWER})
    public void shouldNotGetPlanBecauseNotExists() throws Exception {
        Mockito.doThrow(NotFoundException.class).when(planRepository).unique(NOT_EXISTS_ID);

        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, NOT_EXISTS_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @WithMockUser(username = "user")
    public void shouldNotGetPlanBecauseNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    public void shouldNotGetPlanBecauseNotSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_PLAN_PATH + ApiURI.ID_PATH_PARAM, ApiVersionService.Version.v1, PLAN_ID)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

}