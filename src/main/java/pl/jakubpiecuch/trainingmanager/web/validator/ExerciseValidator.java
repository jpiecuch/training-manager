package pl.jakubpiecuch.trainingmanager.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

public class ExerciseValidator implements Validator {

    private String[] langs;

    @Override
    public boolean supports(Class<?> clazz) {
        return Exercise.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Exercise e = (Exercise) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "partyMuscles", "exercise.partyMuscles.empty.error");

        for (String lang : langs) {
            String name = e.getNames().get(lang);
            if (StringUtils.isEmpty(name)) {
                errors.rejectValue("names['"+lang+"']", "exercise.name.empty.error");
            }
        }
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }
}
