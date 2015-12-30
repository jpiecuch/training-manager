package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.api.v1.TranslatesService;
import pl.jakubpiecuch.trainingmanager.service.repository.RepositoryType;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;

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

    protected TranslatesService translateService;

    protected AuthenticationService authenticationService;

    protected Repository planRepository;

    protected void setUp() {
        authenticationService = Mockito.mock(AuthenticationService.class);
        translateService = Mockito.mock(TranslatesService.class);
        planRepository = Mockito.mock(Repository.class);
        Mockito.when(versionService.translates()).thenReturn(translateService);
        Mockito.when(versionService.authentication()).thenReturn(authenticationService);
        Mockito.when(versionService.store(RepositoryType.PLAN)).thenReturn(planRepository);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
