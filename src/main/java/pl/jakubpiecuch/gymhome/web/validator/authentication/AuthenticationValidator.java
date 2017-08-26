package pl.jakubpiecuch.gymhome.web.validator.authentication;

import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-11-29.
 */
public class AuthenticationValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull(target);
        Assert.notNull(errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", RestrictionCode.REQUIRED);
        Authentication authentication = (Authentication) target;
        if (authentication.getProvider() == null) {
            errors.rejectValue("provider", RestrictionCode.REQUIRED);
        } else if (Provider.Type.SOCIAL == authentication.getProvider() && authentication.getSocial() == null) {
            errors.rejectValue("social", RestrictionCode.REQUIRED);
        }
        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }
}
