package pl.jakubpiecuch.trainingmanager.web.validator.equipment.neck;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-12-07.
 */
public class NeckValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "length", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strength", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", RestrictionCode.REQUIRED);

        Equipment.Neck object = (Equipment.Neck) target;
        try {
            if (object.getConfig() == null) {
                errors.rejectValue("config", RestrictionCode.REQUIRED);
            } else if (object.getConfig().getType() == null) {
                errors.rejectValue("config.type", RestrictionCode.REQUIRED);
            }
        } catch (Exception e) {
            errors.rejectValue("config", RestrictionCode.INVALID);
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

}
