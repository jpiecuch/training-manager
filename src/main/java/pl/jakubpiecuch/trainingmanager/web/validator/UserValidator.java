package pl.jakubpiecuch.trainingmanager.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public class UserValidator implements Validator {
    
    private int maxNameLength;
    private int minNameLength;
    private String namePattern;
    private String emailPattern;
    private Validator passwordValidator;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "user.firstName.error");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "user.lastName.error");
        
        passwordValidator.validate(user, errors);

        if (StringUtils.isEmpty(user.getEmail())) {
            errors.rejectValue("password", "user.email.empty.error");
        } else if (!user.getEmail().matches(emailPattern)) {
            errors.rejectValue("email", "user.email.pattern.error");
        }
        if (StringUtils.isEmpty(user.getName())) { 
            errors.rejectValue("name", "user.email.name.error");
        } else if (!user.getName().matches(namePattern)) {
            errors.rejectValue("name", "user.name.pattern.error");
        } else if (minNameLength > user.getName().length()) {
            errors.rejectValue("name", "user.name.minLength.error");
        } else if (maxNameLength < user.getName().length()) {
            errors.rejectValue("name", "user.name.maxLength.error");
        }
    }

    @Autowired
    public void setPasswordValidator(Validator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void setEmailPattern(String emailPattern) {
        this.emailPattern = emailPattern;
    }

}
