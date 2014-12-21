package pl.jakubpiecuch.trainingmanager.web.validator.registration;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

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
    private Validator authenticationValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Registration object = (Registration) target;
        authenticationValidator.validate(target, errors);
        if (!object.getPassword().matches(passwordPattern)) {
            errors.rejectValue("password", RestrictionCode.PATTERN);
        } else if (!object.getPassword().equals(object.getRepeat())) {
            errors.rejectValue("password", RestrictionCode.EQUAL);
        } else if (minPasswordLength > object.getPassword().length()) {
            errors.rejectValue("password", RestrictionCode.MIN_LENGTH);
        } else if (maxPasswordLength < object.getPassword().length()) {
            errors.rejectValue("password", RestrictionCode.MAX_LENGTH);
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", RestrictionCode.REQUIRED);

        if (StringUtils.isEmpty(object.getEmail())) {
            errors.rejectValue("email", RestrictionCode.REQUIRED);
        } else if (!object.getEmail().matches(emailPattern)) {
            errors.rejectValue("email", RestrictionCode.PATTERN);
        }
        if (!object.getUsername().matches(namePattern)) {
            errors.rejectValue("username", RestrictionCode.PATTERN);
        } else if (minNameLength > object.getUsername().length()) {
            errors.rejectValue("username", RestrictionCode.MIN_LENGTH);
        } else if (maxNameLength < object.getUsername().length()) {
            errors.rejectValue("username", RestrictionCode.MAX_LENGTH);
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    public void setAuthenticationValidator(Validator authenticationValidator) {
        this.authenticationValidator = authenticationValidator;
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
}
