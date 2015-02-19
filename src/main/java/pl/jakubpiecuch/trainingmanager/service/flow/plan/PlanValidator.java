package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-11-29.
 */
public class PlanValidator implements Validator  {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull(target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phases", RestrictionCode.REQUIRED);
        PlanDto plan = (PlanDto) target;
        if (CollectionUtils.isNotEmpty(plan.getPhases())) {
            for (int i = 0; i < plan.getPhases().size(); i++) {
                PhaseDto phase = plan.getPhases().get(i);
                String phaseName = "phases[" + i + "].";

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + "position", RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + "description", RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + "goal", RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + "weeks", RestrictionCode.REQUIRED);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + "workouts", RestrictionCode.REQUIRED);
                if (CollectionUtils.isNotEmpty(phase.getWorkouts())) {
                    for (int j = 0; j < phase.getWorkouts().size(); j++) {
                        WorkoutDto workout = phase.getWorkouts().get(j);
                        String workoutName = "workouts[" + j + "].";

                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName + "muscles", RestrictionCode.REQUIRED);
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName + "weekDay", RestrictionCode.REQUIRED);
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName + "position", RestrictionCode.REQUIRED);
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName + "groups", RestrictionCode.REQUIRED);

                        if (CollectionUtils.isNotEmpty(workout.getGroups())) {
                            for(int k = 0; k < workout.getGroups().size(); k++) {
                                GroupDto group = workout.getGroups().get(k);
                                String groupName = "groups[" + k + "].";
                                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName +  groupName + "id", RestrictionCode.REQUIRED);
                                ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName +  groupName + "exercises", RestrictionCode.REQUIRED);

                                if (CollectionUtils.isNotEmpty(group.getExercises())) {
                                    for (int l = 0; l < group.getExercises().size(); l++) {
                                        String exerciseName = "exercises[" + l + "].";
                                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName +  groupName + exerciseName + "descriptionId", RestrictionCode.REQUIRED);
                                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName +  groupName + exerciseName + "sets", RestrictionCode.REQUIRED);
                                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, phaseName + workoutName +  groupName + exerciseName + "position", RestrictionCode.REQUIRED);
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
