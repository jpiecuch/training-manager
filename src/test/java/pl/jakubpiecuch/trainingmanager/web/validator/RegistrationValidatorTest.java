package pl.jakubpiecuch.trainingmanager.web.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.registration.RegistrationValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-validator-context.xml")
public class RegistrationValidatorTest {
    
    @Autowired
    @Qualifier("registrationValidator")
    private RegistrationValidator validator;

    @Autowired
    private ReadRepository accountRepository;

    @Test(expected = ValidationException.class)
    public void validateExceptionTest() {
        Registration registration = new Registration();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");
        validator.validate(registration, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateTest() {
        Mockito.when(accountRepository.read(new AccountCriteria().addEmailRestrictions("test@test.com"))).thenReturn(new PageResult() {
            @Override
            public List getResult() {
                return new ArrayList();
            }

            @Override
            public long getCount() {
                return 0;
            }
        });

        Mockito.when(accountRepository.read(new AccountCriteria().addNameRestrictions("test123"))).thenReturn(new PageResult() {
            @Override
            public List getResult() {
                return new ArrayList();
            }

            @Override
            public long getCount() {
                return 0;
            }
        });

        Registration registration = new Registration();

        registration.setEmail("test@test.com");
        registration.setFirstName("test");
        registration.setLastName("test");
        registration.setUsername("test123");
        registration.setPassword("passWord123!");
        registration.setProvider(Provider.Type.LOCAL);
        registration.setAccepted(true);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");
        validator.validate(registration, errors);
        
        assertFalse(errors.hasErrors());
    }
}
