package pl.jakubpiecuch.gymhome.web.validator.equipment.press;

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
public class PressValidator extends ConfigValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "length", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strength", RestrictionCode.REQUIRED);

        Equipment.Press object = (Equipment.Press) target;
        validateConfig(object, errors);
        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    @Override
    public void validateConfigProperties(Equipment target, Errors errors) throws IOException {
        Equipment.Press object = (Equipment.Press) target;
        if (object.getConfig().getHandles() == null) {
            errors.rejectValue("config.handles", RestrictionCode.REQUIRED);
        }
    }

}
