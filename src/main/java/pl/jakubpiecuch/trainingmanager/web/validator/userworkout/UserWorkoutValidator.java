package pl.jakubpiecuch.trainingmanager.web.validator.userworkout;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2015-03-28.
 */
public class UserWorkoutValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", RestrictionCode.REQUIRED);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

}
