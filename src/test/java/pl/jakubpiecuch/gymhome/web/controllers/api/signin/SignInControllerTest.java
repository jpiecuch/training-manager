package pl.jakubpiecuch.gymhome.web.controllers.api.signin;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;
import pl.jakubpiecuch.gymhome.web.controllers.api.BaseControllerTestCase;

public class SignInControllerTest extends BaseControllerTestCase {

    private static Authentication VALID_AUTHENTICATION = new Authentication();

    @Autowired
    SignInController controller;

    @Before
    public void setUp() {
        VALID_AUTHENTICATION.setUsername("test");
        VALID_AUTHENTICATION.setPassword("test");
        super.setUp();
    }

    @Test
    public void shouldSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_SIGN_IN_PATH, ApiVersionService.Version.v1)
                .content(OBJECT_MAPPER.writeValueAsString(VALID_AUTHENTICATION)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldNotSignInBecauseAlreadyIs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_SIGN_IN_PATH, ApiVersionService.Version.v1)
                .content(OBJECT_MAPPER.writeValueAsString(VALID_AUTHENTICATION)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldGetSignedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_SIGN_IN_PATH, ApiVersionService.Version.v1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotGetUserBecauseNotSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiURI.API_SIGN_IN_PATH, ApiVersionService.Version.v1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}