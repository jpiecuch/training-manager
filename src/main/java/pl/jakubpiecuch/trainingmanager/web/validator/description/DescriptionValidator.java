package pl.jakubpiecuch.trainingmanager.web.validator.description;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-12-07.
 */
public class DescriptionValidator implements Validator {


    private int minNameLength;
    private int maxNameLength;
    private String[] langs;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Description object = (Description) target;

        for (String lang : langs) {
            String name = object.getNames().get(lang);
            if (StringUtils.isEmpty(name)) {
                errors.rejectValue("names[" + lang + "]", RestrictionCode.REQUIRED);
            } else if (minNameLength > name.length()) {
                errors.rejectValue("names[" + lang + "]", String.format(RestrictionCode.MIN_LENGTH, minNameLength));
            } else if (maxNameLength < name.length()) {
                errors.rejectValue("names[" + lang + "]", String.format(RestrictionCode.MAX_LENGTH, maxNameLength));
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "muscles", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "level", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mechanics", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "force", RestrictionCode.REQUIRED);

        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }
}
