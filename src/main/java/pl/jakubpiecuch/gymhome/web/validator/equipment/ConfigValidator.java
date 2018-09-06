package pl.jakubpiecuch.gymhome.web.validator.equipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.Equipment;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

import java.io.IOException;

/**
 * Created by jakub on 20.12.2015.
 */
public abstract class ConfigValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigValidator.class);

    public void validateConfigProperties(Equipment target, Errors errors) throws IOException {
        //override if validation is necessary
    }
    public void validateConfig(Equipment object, Errors errors) {
        try {
            if (object.getConfig() == null) {
                errors.rejectValue("config", RestrictionCode.REQUIRED);
            } else {
                validateConfigProperties(object, errors);
            }
        } catch (IOException e) {
            LOGGER.warn("", e);
            errors.rejectValue("config", RestrictionCode.INVALID);
        }
    }
}
