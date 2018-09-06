package pl.jakubpiecuch.gymhome.service.flow.plan;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.*;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rico on 2014-11-29.
 */
public class InsertPlanValidator implements Validator {

    public static final String GROUPS_FIELD = "groups";
    public static final String WORKOUTS_FIELD = "workouts";
    public static final String PHASES_FIELD = "phases";
    public static final String EXERCISES_FIELD = "exercises";
    public static final String SETS_FIELD = "sets";
    private static final String ARRAY_PROPERTY_FORMAT = "%s[%s]";
    private static final char SEPARATOR = '.';

    @Override
    public boolean supports(Class<?> clazz) {
        return PlanDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, Plan.NAME_FIELD, RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PHASES_FIELD, RestrictionCode.REQUIRED);
        PlanDto plan = (PlanDto) target;
        if (CollectionUtils.isNotEmpty(plan.getPhases())) {
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < plan.getPhases().size(); i++) {
                positions.add(validatePhase(plan.getPhases().get(i), errors, i));
            }
            validatePositions(positions, plan.getPhases().size(), errors, PHASES_FIELD);
        } else {
            errors.rejectValue(PHASES_FIELD, RestrictionCode.REQUIRED);
        }
        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    private void validatePositions(List<Integer> positions, int size, Errors errors, String field) {
        List<Integer> sorted = positions.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
        List<Integer> expected = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            expected.add(i);
        }

        if (!sorted.equals(expected)) {
            errors.rejectValue(field, RestrictionCode.INVALID_ORDER);
        }
    }

    private Integer validatePhase(PhaseDto phase, Errors errors, int index) {
        String phaseName = String.format(ARRAY_PROPERTY_FORMAT, PHASES_FIELD, index);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, Phase.POSITION_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, Phase.DESCRIPTION_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, Phase.GOAL_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, Phase.WEEKS_FIELD), RestrictionCode.REQUIRED);

        if (CollectionUtils.isNotEmpty(phase.getWorkouts())) {
            for (int i = 0; i < phase.getWorkouts().size(); i++) {
                validateWorkout(phase.getWorkouts().get(i), errors, i, phaseName);
            }
        } else {
            errors.rejectValue(path(phaseName, WORKOUTS_FIELD), RestrictionCode.REQUIRED);
        }
        return phase.getPosition();
    }

    private void validateWorkout(WorkoutDto workout, Errors errors, int index, String phaseName) {
        String workoutName = String.format(ARRAY_PROPERTY_FORMAT, WORKOUTS_FIELD, index);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, Workout.MUSCLES_FIELD), RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, Workout.WEEK_DAY_FIELD), RestrictionCode.REQUIRED);

        if (CollectionUtils.isNotEmpty(workout.getGroups())) {
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < workout.getGroups().size(); i++) {
                positions.add(validateGroup(workout.getGroups().get(i), errors, i, phaseName, workoutName));
            }
            validatePositions(positions, workout.getGroups().size(), errors, path(phaseName, workoutName, GROUPS_FIELD));
        } else {
            errors.rejectValue(path(phaseName, workoutName, GROUPS_FIELD), RestrictionCode.REQUIRED);
        }
    }

    private Integer validateGroup(GroupDto group, Errors errors, int index, String phaseName, String workoutName) {
        String groupName = String.format(ARRAY_PROPERTY_FORMAT, GROUPS_FIELD, index);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, path(phaseName, workoutName, groupName, CommonEntity.ID_FIELD_NAME), RestrictionCode.REQUIRED);

        if (CollectionUtils.isNotEmpty(group.getExercises())) {
            List<Integer> positions = new ArrayList<>();
            for (int l = 0; l < group.getExercises().size(); l++) {
                positions.add(validateExercise(group.getExercises().get(l), errors, l, phaseName, workoutName, groupName));
            }
            validatePositions(positions, group.getExercises().size(), errors, path(phaseName, workoutName, groupName, EXERCISES_FIELD));
        } else {
            errors.rejectValue(path(phaseName, workoutName, groupName, EXERCISES_FIELD), RestrictionCode.REQUIRED);
        }
        return group.getId();
    }

    private Integer validateExercise(ExerciseDto exercise, Errors errors, int index, String phaseName, String workoutName, String groupName) {
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
        return exercise.getPosition();
    }

    private String path(String... chunks) {
        return StringUtils.join(chunks, SEPARATOR);
    }
}
