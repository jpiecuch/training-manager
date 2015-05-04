package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-11-29.
 */
public class PlanValidator implements Validator {

    private static final String ARRAY_PROPERTY_FORMAT = "%s[%s]";
    public static final String GROUPS_FIELD = "groups";
    public static final String WORKOUTS_FIELD = "workouts";
    public static final String PHASES_FIELD = "phases";
    public static final String EXERCISES_FIELD = "exercises";
    private static final char SEPARATOR = '.';
    private static final String SETS_FIELD = "sets";

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PHASES_FIELD, RestrictionCode.REQUIRED);
        PlanDto plan = (PlanDto) target;
        if (CollectionUtils.isNotEmpty(plan.getPhases())) {
            for (int i = 0; i < plan.getPhases().size(); i++) {
                PhaseDto phase = plan.getPhases().get(i);
                String phaseName = String.format(ARRAY_PROPERTY_FORMAT, PHASES_FIELD, i);

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, "position"), RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName,"description"), RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName,"goal"), RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName,"weeks"), RestrictionCode.REQUIRED);

                if (CollectionUtils.isNotEmpty(phase.getWorkouts())) {
                    for (int j = 0; j < phase.getWorkouts().size(); j++) {
                        WorkoutDto workout = phase.getWorkouts().get(j);
                        String workoutName = String.format(ARRAY_PROPERTY_FORMAT, WORKOUTS_FIELD, j);

                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, "muscles"), RestrictionCode.REQUIRED);
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, "weekDay"), RestrictionCode.REQUIRED);
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, "position"), RestrictionCode.REQUIRED);

                        if (CollectionUtils.isNotEmpty(workout.getGroups())) {
                            for (int k = 0; k < workout.getGroups().size(); k++) {

                                GroupDto group = workout.getGroups().get(k);
                                String groupName = String.format(ARRAY_PROPERTY_FORMAT, GROUPS_FIELD, k);
                                ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, "id"), RestrictionCode.REQUIRED);

                                if (CollectionUtils.isEmpty(group.getExercises())) {
                                    errors.rejectValue(path(phaseName, workoutName, groupName, EXERCISES_FIELD), RestrictionCode.REQUIRED);
                                }
                                if (CollectionUtils.isNotEmpty(group.getExercises())) {
                                    for (int l = 0; l < group.getExercises().size(); l++) {
                                        String exerciseName = String.format(ARRAY_PROPERTY_FORMAT, EXERCISES_FIELD, l);
                                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, exerciseName, "descriptionId"), RestrictionCode.REQUIRED);
                                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, exerciseName, "position"), RestrictionCode.REQUIRED);

                                        String[] sets = group.getExercises().get(l).getSets();
                                        if (ArrayUtils.isNotEmpty(sets)) {
                                            for (int m = 0; m < sets.length; m++) {
                                                String set = sets[m];
                                                if (!Exercise.FAIL_KEY.equals(set) && !NumberUtils.isNumber(set)) {
                                                    errors.rejectValue(path(phaseName, workoutName, groupName, exerciseName, String.format(ARRAY_PROPERTY_FORMAT, SETS_FIELD, m)), RestrictionCode.INVALID);
                                                }
                                            }
                                        } else {
                                            errors.rejectValue(path(phaseName, workoutName, groupName, exerciseName, SETS_FIELD), RestrictionCode.REQUIRED);
                                        }
                                    }
                                }
                            }
                        } else {
                            errors.rejectValue(path(phaseName, workoutName, GROUPS_FIELD), RestrictionCode.REQUIRED);
                        }

                    }
                } else {
                    errors.rejectValue(path(phaseName + WORKOUTS_FIELD), RestrictionCode.REQUIRED);
                }

            }
        } else {
            errors.rejectValue(PHASES_FIELD, RestrictionCode.REQUIRED);
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    private String path(String... chunks) {
        return StringUtils.join(chunks, SEPARATOR);
    }
}
