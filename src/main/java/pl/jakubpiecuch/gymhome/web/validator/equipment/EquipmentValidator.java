package pl.jakubpiecuch.gymhome.web.validator.equipment;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.Equipment;

import java.util.Map;

/**
 * Created by Rico on 2014-12-07.
 */
public class EquipmentValidator implements Validator {

    private Map<Equipment.Type, Validator> validators;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Equipment object = (Equipment) target;
        validators.get(object.getType()).validate(object, errors);
    }

    public void setValidators(Map<Equipment.Type, Validator> validators) {
        this.validators = validators;
    }
}
