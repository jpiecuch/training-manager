package pl.jakubpiecuch.trainingmanager.web.validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class AuthenticationValidatorTest extends AbstractBaseTest {
    
    @Autowired
    @Qualifier("authenticationValidator")
    private Validator validator;

    @Test
    public void validateTest() {
        Authentication registration = new Authentication();
        Response<Authentication> response = new Response<Authentication>();
        validator.isValid(registration, response, "");
        
        assertTrue(response.hasErrors());

        registration.setUsername("test123");
        registration.setPassword("passWord123!");
        response = new Response<Authentication>();
        validator.isValid(registration, response, "");
        
        assertFalse(response.hasErrors());
    }
}
