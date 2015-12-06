package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.*;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, Plan.NAME_FIELD, RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PHASES_FIELD, RestrictionCode.REQUIRED);
        PlanDto plan = (PlanDto) target;
        if (CollectionUtils.isNotEmpty(plan.getPhases())) {
            for (int i = 0; i < plan.getPhases().size(); i++) {
                validatePhase(plan.getPhases().get(i), errors, i);
            }
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    private void validatePhase(PhaseDto phase, Errors errors, int index) {
        String phaseName = String.format(ARRAY_PROPERTY_FORMAT, PHASES_FIELD, index);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, Phase.POSITION_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName,Phase.DESCRIPTION_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName,Phase.GOAL_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName,Phase.WEEKS_FIELD), RestrictionCode.REQUIRED);

        if (CollectionUtils.isNotEmpty(phase.getWorkouts())) {
            for (int i = 0; i < phase.getWorkouts().size(); i++) {
                validateWorkout(phase.getWorkouts().get(i), errors, i, phaseName);
            }
        } else {
            errors.rejectValue(path(phaseName + WORKOUTS_FIELD), RestrictionCode.REQUIRED);
        }
    }

    private void validateWorkout(WorkoutDto workout, Errors errors, int index, String phaseName) {
        String workoutName = String.format(ARRAY_PROPERTY_FORMAT, WORKOUTS_FIELD, index);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, Workout.MUSCLES_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, Workout.WEEK_DAY_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, Workout.POSITION_FIELD), RestrictionCode.REQUIRED);

        if (CollectionUtils.isNotEmpty(workout.getGroups())) {
            for (int i = 0; i < workout.getGroups().size(); i++) {
                validateGroup(workout.getGroups().get(i), errors, i, phaseName, workoutName);
            }
        } else {
            errors.rejectValue(path(phaseName, workoutName, GROUPS_FIELD), RestrictionCode.REQUIRED);
        }
    }

    private void validateGroup(GroupDto group, Errors errors, int index, String phaseName, String workoutName) {
        String groupName = String.format(ARRAY_PROPERTY_FORMAT, GROUPS_FIELD, index);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, CommonEntity.ID_FIELD), RestrictionCode.REQUIRED);

        if (CollectionUtils.isEmpty(group.getExercises())) {
            errors.rejectValue(path(phaseName, workoutName, groupName, EXERCISES_FIELD), RestrictionCode.REQUIRED);
        }
        if (CollectionUtils.isNotEmpty(group.getExercises())) {
            for (int l = 0; l < group.getExercises().size(); l++) {
                validateExercise(group.getExercises().get(l), errors, l, phaseName, workoutName, groupName);
            }
        }
    }

    private void validateExercise(ExerciseDto exercise, Errors errors, int index, String phaseName, String workoutName, String groupName) {
        String exerciseName = String.format(ARRAY_PROPERTY_FORMAT, EXERCISES_FIELD, index);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, exerciseName, ExerciseDto.DESCRIPTION_ID), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, exerciseName, Exercise.POSITION_FIELD), RestrictionCode.REQUIRED);

        String[] sets = exercise.getSets();
        if (ArrayUtils.isNotEmpty(sets)) {
            for (int i = 0; i < sets.length; i++) {
                String set = sets[i];
                if (!Exercise.FAIL_KEY.equals(set) && !NumberUtils.isNumber(set)) {
                    errors.rejectValue(path(phaseName, workoutName, groupName, exerciseName, String.format(ARRAY_PROPERTY_FORMAT, SETS_FIELD, i)), RestrictionCode.INVALID);
                }
            }
        } else {
            errors.rejectValue(path(phaseName, workoutName, groupName, exerciseName, SETS_FIELD), RestrictionCode.REQUIRED);
        }
    }

    private String path(String... chunks) {
        return StringUtils.join(chunks, SEPARATOR);
    }
}
