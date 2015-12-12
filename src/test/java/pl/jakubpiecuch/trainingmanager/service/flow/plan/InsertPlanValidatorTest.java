package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.*;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

import javax.annotation.Nullable;

import static org.junit.Assert.*;

/**
 * Created by jpiecuch on 2015-11-20.
 */
public class InsertPlanValidatorTest {

    protected static final java.lang.String NAME = "plan";
    private InsertPlanValidator validator = new InsertPlanValidator();
    private PlanDto plan = new PlanDto();

    public Validator getValidator() {
        return validator;
    }

    public PlanDto getPlan() {
        return plan;
    }

    @Test
    public void testValidate() throws Exception {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(getPlan(), NAME);

        boolean assertionPerformed = false;
        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(2, errors.getFieldErrorCount());

            assertRequiredFields(errors, Plan.NAME_FIELD, InsertPlanValidator.PHASES_FIELD);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        getPlan().setName("name");
        getPlan().getPhases().add(new PhaseDto());

        assertionPerformed = false;

        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(5, errors.getFieldErrorCount());

            validatePhase(0, errors);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        getPlan().getPhases().get(0).setDescription("description");
        getPlan().getPhases().get(0).setGoal(Plan.Goal.MUSCLES);
        getPlan().getPhases().get(0).setPosition(1);
        getPlan().getPhases().get(0).setWeeks(3);
        getPlan().getPhases().get(0).getWorkouts().add(new WorkoutDto());

        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        assertionPerformed = false;

        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(4, errors.getFieldErrorCount());

            validateWorkout(0, 0, errors);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        getPlan().getPhases().get(0).getWorkouts().get(0).setPosition(1);
        getPlan().getPhases().get(0).getWorkouts().get(0).setMuscles(new Description.Muscles[] {Description.Muscles.ABDUCTORS});
        getPlan().getPhases().get(0).getWorkouts().get(0).setWeekDay(Workout.WeekDay.FRIDAY);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().add(new GroupDto());

        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        assertionPerformed = false;

        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(2, errors.getFieldErrorCount());

            validateGroup(0, 0, 0, errors);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).setId(1);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().add(new ExerciseDto());

        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        assertionPerformed = false;

        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(3, errors.getFieldErrorCount());

            validateExercise(0, 0, 0, 0, errors, ExerciseDto.DESCRIPTION_ID, Exercise.POSITION_FIELD, InsertPlanValidator.SETS_FIELD);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setPosition(1);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setDescriptionId(1l);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setSets(new String[] {"wrong"});


        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        assertionPerformed = false;

        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(1, errors.getFieldErrorCount());

            String field = InsertPlanValidator.PHASES_FIELD + "[0]." + InsertPlanValidator.WORKOUTS_FIELD
                    + "[0]." + InsertPlanValidator.GROUPS_FIELD + "[0]." + InsertPlanValidator.EXERCISES_FIELD + "[0].sets[0]";

            assertEquals(1, errors.getFieldErrors(field).size());
            assertEquals(RestrictionCode.INVALID, errors.getFieldErrors(field).get(0).getCode());

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setSets(new String[] {"12", Exercise.FAIL_KEY});


        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        getValidator().validate(getPlan(), errors);

        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getFieldErrorCount());

    }

    @Test
    public void testSupports() {
        assertTrue(getValidator().supports(PlanDto.class));
        assertFalse(getValidator().supports(Plan.class));
    }

    private String[] extendFields(final String prefix, String... fields) {
        return Lists.transform(Lists.newArrayList(fields), new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String string) {
                return prefix + string;
            }
        }).toArray(new String[fields.length]);
    }

    private void validateExercise(int phaseIndex, int workoutIndex, int groupIndex, int exerciseIndex, Errors errors, String... fields) {
        String exercisePrefix = InsertPlanValidator.PHASES_FIELD + "[" + phaseIndex + "]." + InsertPlanValidator.WORKOUTS_FIELD
                + "[" + workoutIndex + "]." + InsertPlanValidator.GROUPS_FIELD + "[" + groupIndex + "]." + InsertPlanValidator.EXERCISES_FIELD + "[" + exerciseIndex + "].";
        assertRequiredFields(errors, extendFields(exercisePrefix, fields));
    }

    private void validateGroup(int phaseIndex, int workoutIndex, int groupIndex, Errors errors) {
        String groupPrefix = InsertPlanValidator.PHASES_FIELD + "[" + phaseIndex + "]." + InsertPlanValidator.WORKOUTS_FIELD + "[" + workoutIndex + "]." + InsertPlanValidator.GROUPS_FIELD + "[" + groupIndex + "].";
        assertRequiredFields(errors, groupPrefix + CommonEntity.ID_FIELD_NAME, groupPrefix + InsertPlanValidator.EXERCISES_FIELD);
    }


    private void validateWorkout(int phaseIndex, int workoutIndex, Errors errors) {
        String workoutPrefix = InsertPlanValidator.PHASES_FIELD + "[" + phaseIndex + "]." + InsertPlanValidator.WORKOUTS_FIELD + "[" + workoutIndex + "].";
        assertRequiredFields(errors, workoutPrefix + Workout.MUSCLES_FIELD,  workoutPrefix + Workout.POSITION_FIELD,
                workoutPrefix + Workout.WEEK_DAY_FIELD, workoutPrefix + InsertPlanValidator.GROUPS_FIELD);
    }

    private void validatePhase(int index, Errors errors) {
        String phaseSuffix = InsertPlanValidator.PHASES_FIELD + "[" + index + "].";
        assertRequiredFields(errors, phaseSuffix + Phase.POSITION_FIELD,  phaseSuffix + Phase.DESCRIPTION_FIELD,
                phaseSuffix + Phase.WEEKS_FIELD, phaseSuffix + Phase.GOAL_FIELD,
                phaseSuffix + InsertPlanValidator.WORKOUTS_FIELD);
    }

    protected void assertRequiredFields(Errors errors, String... fields) {
        for (String field : fields) {
            assertEquals(1, errors.getFieldErrors(field).size());
            assertEquals(RestrictionCode.REQUIRED, errors.getFieldErrors(field).get(0).getCode());
        }
    }
}