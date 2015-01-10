package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-11-29.
 */
public class ExerciseValidator implements Validator  {


    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workoutId", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descriptionId", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sets", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "group", RestrictionCode.REQUIRED);
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
