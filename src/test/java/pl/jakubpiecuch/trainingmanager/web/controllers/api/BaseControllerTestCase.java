package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;

import java.util.HashMap;

/**
 * Created by Rico on 2014-12-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:test-controller-context.xml")
public abstract class BaseControllerTestCase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected ApiVersionService versionService;

    protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected MockMvc mockMvc;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
