package pl.jakubpiecuch.trainingmanager.web.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-validator-context.xml")
public class RegistrationValidatorTest {
    
    @Autowired
    @Qualifier("registrationValidator")
    private Validator validator;

    @Test(expected = ValidationException.class)
    public void validateExceptionTest() {
        Registration registration = new Registration();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");
        validator.validate(registration, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateTest() {
        Registration registration = new Registration();

        registration.setEmail("test@test.com");
        registration.setFirstName("test");
        registration.setLastName("test");
        registration.setUsername("test123");
        registration.setPassword("passWord123!");
        registration.setRepeat("passWord123!");
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");
        validator.validate(registration, errors);
        
        assertFalse(errors.hasErrors());
    }
}
