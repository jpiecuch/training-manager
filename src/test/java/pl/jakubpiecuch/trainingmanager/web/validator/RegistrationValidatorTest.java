package pl.jakubpiecuch.trainingmanager.web.validator;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;


public class RegistrationValidatorTest extends AbstractBaseTest {
    
    @Autowired
    @Qualifier("registrationValidator")
    private Validator validator;

    @Test
    public void validateTest() {
        Registration registration = new Registration();
        Response<Registration> response = new Response<Registration>();
        validator.isValid(registration, response, "");
        
        assertTrue(response.hasErrors());

        registration.setEmail("test@test.com");
        registration.setFirstName("test");
        registration.setLastName("test");
        registration.setUsername("test123");
        registration.setPassword("passWord123!");
        registration.setRepeat("passWord123!");
        response = new Response<Registration>();
        validator.isValid(registration, response, "");
        
        assertFalse(response.hasErrors());
    }
}
