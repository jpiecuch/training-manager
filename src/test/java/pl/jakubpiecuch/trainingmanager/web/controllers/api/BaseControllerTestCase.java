package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;

/**
 * Created by Rico on 2014-12-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:context/unit/test-controller-context.xml")
public abstract class BaseControllerTestCase {

    protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    protected ApiVersionService versionService;
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
