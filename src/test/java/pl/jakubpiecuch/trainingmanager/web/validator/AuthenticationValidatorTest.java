package pl.jakubpiecuch.trainingmanager.web.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-validator-context.xml")
public class AuthenticationValidatorTest {

    public static final String AUTH_OBJECT_NAME = "authentication";

    @Autowired
    @Qualifier("authenticationValidator")
    private Validator validator;

    @Test(expected = IllegalArgumentException.class)
    public void validateNull() {
        validator.validate(null, null);
    }

    @Test(expected = ValidationException.class)
    public void validateExceptionEmptyAllFields() {
        Authentication authentication = new Authentication();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(authentication, AUTH_OBJECT_NAME);
        validator.validate(authentication, errors);

        assertTrue(errors.hasErrors());
    }

    @Test(expected = ValidationException.class)
    public void validateExceptionEmptySocial() {
        Authentication authentication = new Authentication();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(authentication, AUTH_OBJECT_NAME);
        validator.validate(authentication, errors);

        assertTrue(errors.hasErrors());
    }

    @Test(expected = ValidationException.class)
    public void validateExceptionEmptyProvider() {
        Authentication authentication = new Authentication();
        authentication.setUsername("test123");
        authentication.setPassword("passWord123!");
        authentication.setProvider(Provider.Type.SOCIAL);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(authentication, AUTH_OBJECT_NAME);
        validator.validate(authentication, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateTest() {
        Authentication authentication = new Authentication();
        authentication.setUsername("test123");
        authentication.setPassword("passWord123!");
        authentication.setProvider(Provider.Type.LOCAL);

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(authentication, AUTH_OBJECT_NAME);

        validator.validate(authentication, errors);

        assertFalse(errors.hasErrors());


        authentication.setProvider(Provider.Type.SOCIAL);
        authentication.setSocial(SocialProvider.SocialType.FACEBOOK);

        errors = new BeanPropertyBindingResult(authentication, AUTH_OBJECT_NAME);

        validator.validate(authentication, errors);

        assertFalse(errors.hasErrors());
    }
}
