package pl.jakubpiecuch.gymhome.web.validator.description;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-12-07.
 */
public class DescriptionValidator implements Validator {

    private int minNameLength;
    private int maxNameLength;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Description object = (Description) target;

        if (StringUtils.isEmpty(object.getName())) {
            errors.rejectValue("name", RestrictionCode.REQUIRED);
        } else if (minNameLength > object.getName().length()) {
            errors.rejectValue("name", String.format(RestrictionCode.MIN_LENGTH, minNameLength));
        } else if (maxNameLength < object.getName().length()) {
            errors.rejectValue("name", String.format(RestrictionCode.MAX_LENGTH, maxNameLength));
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "muscles", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "level", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mechanics", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "force", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lateral", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sets", RestrictionCode.REQUIRED);

        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }
}
