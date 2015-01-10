package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-11-29.
 */
public class PhaseValidator implements Validator  {


    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "goal", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "planId", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weeks", RestrictionCode.REQUIRED);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
