package pl.jakubpiecuch.trainingmanager.web.validator.equipment.stand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

import java.io.IOException;

/**
 * Created by Rico on 2014-12-07.
 */
public class StandValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strength", RestrictionCode.REQUIRED);

        Equipment.Stand object = (Equipment.Stand) target;
        try {
            if (object.getConfig() == null) {
                errors.rejectValue("config", RestrictionCode.REQUIRED);
            } else {
                if (object.getConfig().getLevels() == null) {
                    errors.rejectValue("config.level", RestrictionCode.REQUIRED);
                }
                if (object.getConfig().getHeight() == null) {
                    errors.rejectValue("config.height", RestrictionCode.REQUIRED);
                } else {
                    if (object.getConfig().getHeight().getMin() == null) {
                        errors.rejectValue("config.height.min", RestrictionCode.REQUIRED);
                    }
                    if (object.getConfig().getHeight().getMax() == null) {
                        errors.rejectValue("config.height.max", RestrictionCode.REQUIRED);
                    }
                    if (object.getConfig().getHeight().getMin() != null && object.getConfig().getHeight().getMax() != null
                            && object.getConfig().getHeight().getMin() >= object.getConfig().getHeight().getMax()) {
                        errors.rejectValue("config.height.min", String.format(RestrictionCode.LOWER, "config.height.max"));
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.warn("", e);
            errors.rejectValue("config", RestrictionCode.INVALID);
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

}
