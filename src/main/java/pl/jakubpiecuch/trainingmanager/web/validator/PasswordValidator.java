package pl.jakubpiecuch.trainingmanager.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public class PasswordValidator implements Validator {
    
    private int minPasswordLength;
    private int maxPasswordLength;
    private String passwordPattern;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;
        
        if (StringUtils.isEmpty(user.getPassword())) {
            errors.rejectValue("password", "user.password.empty.error");
        } else if (!user.getPassword().matches(passwordPattern)) {
            errors.rejectValue("password", "user.password.pattern.error");
        } else if (!user.getPassword().equals(user.getrPassword())) {
            errors.rejectValue("rPassword", "user.password.unique.error");
        } else if (minPasswordLength > user.getPassword().length()) {
            errors.rejectValue("name", "user.password.minLength.error");
        } else if (maxPasswordLength < user.getPassword().length()) {
            errors.rejectValue("name", "user.password.maxLength.error");
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
}
