package pl.jakubpiecuch.trainingmanager.web.validator;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Users;


public class UserValidatorTest extends AbstractBaseTest {
    
    @Autowired
    @Qualifier("userValidator")
    private Validator validator;
    
    @Test
    public void validateTest() {
        Users user = new Users();
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        
        assertTrue(errors.hasErrors());
        
        user = new Users();
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setName("test123");
        user.setPassword("passWord123!");
        user.setrPassword("passWord123!");
        errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        
        assertFalse(errors.hasErrors());
    }
}
