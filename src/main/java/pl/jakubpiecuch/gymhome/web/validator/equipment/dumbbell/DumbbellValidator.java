package pl.jakubpiecuch.gymhome.web.validator.equipment.dumbbell;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-12-07.
 */
public class DumbbellValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "length", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strength", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", RestrictionCode.REQUIRED);

        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

}
