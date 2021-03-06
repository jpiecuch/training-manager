package pl.jakubpiecuch.gymhome.web.validator.registration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.service.repository.ReadRepository;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.model.Registration;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rico on 2014-12-07.
 */
public class RegistrationValidator implements Validator {

    private int minPasswordLength;
    private int maxPasswordLength;
    private String passwordPattern;
    private String emailPattern;
    private String namePattern;
    private int minNameLength;
    private int maxNameLength;
    private ReadRepository<Account, AccountCriteria> repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Registration object = (Registration) target;
        Assert.notNull(target);
        Assert.notNull(errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, Registration.FIRST_NAME_FIELD, RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, Registration.LAST_NAME_FIELD, RestrictionCode.REQUIRED);

        validateProvider(errors, object);
        validatePassword(errors, object);
        validateEmail(errors, object);
        validateUsername(errors, object);
        validateAccpted(errors, object);

        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    private void validateAccpted(Errors errors, Registration object) {
        if (!object.isAccepted()) {
            errors.rejectValue(Registration.ACCEPTED_FIELD, RestrictionCode.CHECKED);
        }
    }

    private void validateUsername(Errors errors, Registration object) {
        if (StringUtils.isEmpty(object.getUsername())) {
            errors.rejectValue(Registration.USERNAME_FIELD, RestrictionCode.REQUIRED);
        } else if (!object.getUsername().matches(namePattern)) {
            errors.rejectValue(Registration.USERNAME_FIELD, RestrictionCode.PATTERN, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.PATTERN, namePattern).build()}, null);
        } else if (minNameLength > object.getUsername().length()) {
            errors.rejectValue(Registration.USERNAME_FIELD, RestrictionCode.MIN_LENGTH, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.MIN_LENGTH, minNameLength).build()}, null);
        } else if (maxNameLength < object.getUsername().length()) {
            errors.rejectValue(Registration.USERNAME_FIELD, RestrictionCode.MAX_LENGTH, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.MAX_LENGTH, maxNameLength).build()}, null);
        } else if (repository.page(new AccountCriteria().addNameRestrictions(object.getUsername())).getCount() > 0) {
            errors.rejectValue(Registration.USERNAME_FIELD, RestrictionCode.EXISTS);
        }
    }

    private void validateEmail(Errors errors, Registration object) {
        if (StringUtils.isEmpty(object.getEmail())) {
            errors.rejectValue(Registration.EMAIL_FIELD, RestrictionCode.REQUIRED);
        } else if (!object.getEmail().matches(emailPattern)) {
            errors.rejectValue(Registration.EMAIL_FIELD, RestrictionCode.PATTERN, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.PATTERN, emailPattern).build()}, null);
        } else if (repository.page(new AccountCriteria().addEmailRestrictions(object.getEmail()).addProviderRestrictions(Provider.Type.LOCAL)).getCount() > 0) {
            errors.rejectValue(Registration.EMAIL_FIELD, RestrictionCode.EXISTS);
        }
    }

    private void validatePassword(Errors errors, Registration object) {
        if (StringUtils.isEmpty(object.getPassword())) {
            errors.rejectValue(Registration.CREDENTIAL_FIELD, RestrictionCode.REQUIRED);
        } else if (!object.getPassword().matches(passwordPattern)) {
            errors.rejectValue(Registration.CREDENTIAL_FIELD, RestrictionCode.PATTERN, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.PATTERN, passwordPattern).build()}, null);
        } else if (minPasswordLength > object.getPassword().length()) {
            errors.rejectValue(Registration.CREDENTIAL_FIELD, RestrictionCode.MIN_LENGTH, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.MIN_LENGTH, minPasswordLength).build()}, null);
        } else if (maxPasswordLength < object.getPassword().length()) {
            errors.rejectValue(Registration.CREDENTIAL_FIELD, RestrictionCode.MAX_LENGTH, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.MAX_LENGTH, maxPasswordLength).build()}, null);
        }
    }

    private void validateProvider(Errors errors, Registration object) {
        if (object.getProvider() == null) {
            errors.rejectValue(Registration.PROVIDER_FIELD, RestrictionCode.REQUIRED);
        } else if (Provider.Type.SOCIAL == object.getProvider() && object.getSocial() == null) {
            errors.rejectValue(Registration.SOCIAL_FIELD, RestrictionCode.REQUIRED);
        }
    }

    public void setMinPasswordLength(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public void setMaxPasswordLength(int maxPasswordLength) {
        this.maxPasswordLength = maxPasswordLength;
    }

    public void setPasswordPattern(String passwordPattern) {
        this.passwordPattern = passwordPattern;
    }

    public void setEmailPattern(String emailPattern) {
        this.emailPattern = emailPattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public void setRepository(ReadRepository<Account, AccountCriteria> repository) {
        this.repository = repository;
    }

    private class ParamsMapBuilder {

        private Map<String, Object> map = new HashMap<String, Object>();

        ParamsMapBuilder addParam(String key, Object value) {
            map.put(key, value);
            return this;
        }

        Map build() {
            return map;
        }
    }
}
