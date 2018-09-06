package pl.jakubpiecuch.gymhome.web.validator.equipment.barbell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.Equipment;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

import java.io.IOException;

/**
 * Created by Rico on 2014-12-07.
 */
public class BarbellValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarbellValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "length", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strength", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", RestrictionCode.REQUIRED);

        Equipment.Barbell object = (Equipment.Barbell) target;
        try {
            if (object.getConfig() == null) {
                errors.rejectValue("config", RestrictionCode.REQUIRED);
            } else if (object.getConfig().getType() == null) {
                errors.rejectValue("config.type", RestrictionCode.REQUIRED);
            }
        } catch (IOException e) {
            LOGGER.warn("", e);
            errors.rejectValue("config", RestrictionCode.INVALID);
        }
        if (errors.hasErrors()) {
            throw new ValidationException( (BeanPropertyBindingResult) errors);
        }
    }

}
