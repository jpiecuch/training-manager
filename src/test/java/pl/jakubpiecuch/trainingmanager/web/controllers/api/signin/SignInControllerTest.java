package pl.jakubpiecuch.trainingmanager.web.controllers.api.signin;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.BaseControllerTestCase;

public class SignInControllerTest extends BaseControllerTestCase {

    @Autowired
    SignInController controller;

    @Override
    protected SignInController getController() {
        return controller;
    }

    private static Authentication VALID_AUTHENTICATION = new Authentication();

    @Before
    public void setUp() {
        VALID_AUTHENTICATION.setUsername("test");
        VALID_AUTHENTICATION.setPassword("test");
        super.setUp();
    }

    @Test
    public void testSignIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ApiURI.API_SIGN_IN_PATH, ApiVersionService.Version.v1)
                .content(OBJECT_MAPPER.writeValueAsString(VALID_AUTHENTICATION)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testSigned() throws Exception {

    }
}