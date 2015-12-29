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
import pl.jakubpiecuch.trainingmanager.service.flow.plan.ValidationTestUtils;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.registration.RegistrationValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context/unit/test-validator-context.xml")
public class RegistrationValidatorTest {

    @Autowired
    @Qualifier("registrationValidator")
    private RegistrationValidator validator;

    @Autowired
    private ReadRepository accountRepository;

    @Test
    public void validateExceptionTest() {

        Registration registration = new Registration();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registration, "registration");

        try {
            validator.validate(registration, errors);
            fail();
        } catch (ValidationException ex) {

        }
        assertTrue(errors.hasErrors());
        assertEquals(7, errors.getFieldErrorCount());

        ValidationTestUtils.createAssertBuilder()
                .addAssert(Registration.USERNAME_FIELD, RestrictionCode.REQUIRED)
                .addAssert(Registration.FIRST_NAME_FIELD, RestrictionCode.REQUIRED)
                .addAssert(Registration.LAST_NAME_FIELD, RestrictionCode.REQUIRED)
                .addAssert(Registration.PROVIDER_FIELD, RestrictionCode.REQUIRED)
                .addAssert(Registration.EMAIL_FIELD, RestrictionCode.REQUIRED)
                .addAssert(Registration.CREDENTIAL_FIELD, RestrictionCode.REQUIRED)
                .addAssert(Registration.ACCEPTED_FIELD, RestrictionCode.CHECKED)
                .assertion(errors);
    }

    @Test
    public void validateTest() {
        Mockito.when(accountRepository.read(new AccountCriteria().addEmailRestrictions("test@test.com").addProviderRestrictions(Provider.Type.LOCAL))).thenReturn(createPage(new ArrayList(), 0));

        Mockito.when(accountRepository.read(new AccountCriteria().addNameRestrictions("test123"))).thenReturn(createPage(new ArrayList(), 0));

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

    private PageResult createPage(final List list, final long count) {
        return new PageResult() {
            @Override
            public List getResult() {
                return list;
            }

            @Override
            public long getCount() {
                return count;
            }
        };
    }
}
