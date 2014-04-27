package pl.jakubpiecuch.trainingmanager.web.validator;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Users;


public class PasswordValidatorTest extends AbstractBaseTest {
    
    @Autowired
    @Qualifier("passwordValidator")
    private Validator validator;
    
    @Test
    public void validateTest() {
        Users user = new Users();
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        
        assertTrue(errors.hasErrors());
        
        user = new Users();
        user.setPassword("passWord123#");
        user.setrPassword("passWord123#");
        errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        
        assertFalse(errors.hasErrors());
    }
}
