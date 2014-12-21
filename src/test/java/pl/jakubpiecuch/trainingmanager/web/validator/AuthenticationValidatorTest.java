package pl.jakubpiecuch.trainingmanager.web.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-validator-context.xml")
public class AuthenticationValidatorTest {
    
    @Autowired
    @Qualifier("authenticationValidator")
    private Validator validator;

    @Test(expected = ValidationException.class)
    public void validateException() {
        Authentication registration = new Authentication();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");
        validator.validate(registration, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateTest() {
        Authentication registration = new Authentication();
        registration.setUsername("test123");
        registration.setPassword("passWord123!");

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");

        validator.validate(registration, errors);
        
        assertFalse(errors.hasErrors());
    }
}
