package pl.jakubpiecuch.gymhome.web.validator.equipment.stand;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import pl.jakubpiecuch.gymhome.domain.Equipment;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;
import pl.jakubpiecuch.gymhome.web.validator.equipment.ConfigValidator;

import java.io.IOException;

/**
 * Created by Rico on 2014-12-07.
 */
public class StandValidator extends ConfigValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strength", RestrictionCode.REQUIRED);

        Equipment.Stand object = (Equipment.Stand) target;
        validateConfig(object, errors);
        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    @Override
    public void validateConfigProperties(Equipment target, Errors errors) throws IOException {
        Equipment.Stand object = (Equipment.Stand) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "config.level", RestrictionCode.REQUIRED);

        if (object.getConfig().getHeight() == null) {
            errors.rejectValue("config.height", RestrictionCode.REQUIRED);
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "config.height.min", RestrictionCode.REQUIRED);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "config.height.max", RestrictionCode.REQUIRED);

            if (object.getConfig().getHeight().getMin() != null && object.getConfig().getHeight().getMax() != null
                    && object.getConfig().getHeight().getMin() >= object.getConfig().getHeight().getMax()) {
                errors.rejectValue("config.height.min", String.format(RestrictionCode.LOWER, "config.height.max"));
            }
        }
    }
}

