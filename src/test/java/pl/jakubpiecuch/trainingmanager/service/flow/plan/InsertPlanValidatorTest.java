package pl.jakubpiecuch.trainingmanager.service.flow.plan;

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

        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(2, errors.getFieldErrorCount());

            ValidationTestUtils.AssertBuilder assertBuilder = ValidationTestUtils.createAssertBuilder();

            for (String fullField : new String[] {Plan.NAME_FIELD, InsertPlanValidator.PHASES_FIELD}) {
                assertBuilder.addAssert(fullField, RestrictionCode.REQUIRED);
            }

            assertBuilder.assertion(errors);
        }


        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        getPlan().setName("name");
        getPlan().getPhases().add(new PhaseDto());

        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(5, errors.getFieldErrorCount());

            validatePhase(0, errors);
        }

        getPlan().getPhases().get(0).setDescription("description");
        getPlan().getPhases().get(0).setGoal(Plan.Goal.MUSCLES);
        getPlan().getPhases().get(0).setPosition(1);
        getPlan().getPhases().get(0).setWeeks(3);
        getPlan().getPhases().get(0).getWorkouts().add(new WorkoutDto());

        errors = new BeanPropertyBindingResult(getPlan(), NAME);


        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(4, errors.getFieldErrorCount());

            validateWorkout(0, 0, errors);
        }


        getPlan().getPhases().get(0).getWorkouts().get(0).setPosition(1);
        getPlan().getPhases().get(0).getWorkouts().get(0).setMuscles(new Description.Muscles[] {Description.Muscles.ABDUCTORS});
        getPlan().getPhases().get(0).getWorkouts().get(0).setWeekDay(Workout.WeekDay.FRIDAY);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().add(new GroupDto());

        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(2, errors.getFieldErrorCount());

            validateGroup(0, 0, 0, errors);
        }

        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).setId(1);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().add(new ExerciseDto());

        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(3, errors.getFieldErrorCount());

            validateExercise(0, 0, 0, 0, errors, ExerciseDto.DESCRIPTION_ID, Exercise.POSITION_FIELD, InsertPlanValidator.SETS_FIELD);
        }

        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setPosition(1);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setDescriptionId(1l);
        getPlan().getPhases().get(0).getWorkouts().get(0).getGroups().get(0).getExercises().get(0).setSets(new String[] {"wrong"});


        errors = new BeanPropertyBindingResult(getPlan(), NAME);

        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(1, errors.getFieldErrorCount());

            ValidationTestUtils.FieldPathBuilder builder = ValidationTestUtils.createFieldPathBuilder()
                    .addCollectionField(InsertPlanValidator.PHASES_FIELD, 0)
                    .addCollectionField(InsertPlanValidator.WORKOUTS_FIELD, 0)
                    .addCollectionField(InsertPlanValidator.GROUPS_FIELD , 0)
                    .addCollectionField(InsertPlanValidator.EXERCISES_FIELD, 0)
                    .addCollectionField(ExerciseDto.SETS_FIELD, 0);

            ValidationTestUtils.createAssertBuilder().addAssert(builder.build(), RestrictionCode.INVALID);
        }

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

    private void validateExercise(int phaseIndex, int workoutIndex, int groupIndex, int exerciseIndex, Errors errors, String... fields) {
        ValidationTestUtils.FieldPathBuilder builder = ValidationTestUtils.createFieldPathBuilder()
                .addCollectionField(InsertPlanValidator.PHASES_FIELD, phaseIndex)
                .addCollectionField(InsertPlanValidator.WORKOUTS_FIELD, workoutIndex)
                .addCollectionField(InsertPlanValidator.GROUPS_FIELD , groupIndex)
                .addCollectionField(InsertPlanValidator.EXERCISES_FIELD, exerciseIndex);

        ValidationTestUtils.AssertBuilder assertBuilder = ValidationTestUtils.createAssertBuilder();

        for (String fullField : ValidationTestUtils.extendFields(builder, fields)) {
            assertBuilder.addAssert(fullField, RestrictionCode.REQUIRED);
        }

        assertBuilder.assertion(errors);

    }

    private void validateGroup(int phaseIndex, int workoutIndex, int groupIndex, Errors errors) {
        ValidationTestUtils.FieldPathBuilder builder = ValidationTestUtils.createFieldPathBuilder()
                .addCollectionField(InsertPlanValidator.PHASES_FIELD, phaseIndex)
                .addCollectionField(InsertPlanValidator.WORKOUTS_FIELD, workoutIndex)
                .addCollectionField(InsertPlanValidator.GROUPS_FIELD , groupIndex);

        ValidationTestUtils.AssertBuilder assertBuilder = ValidationTestUtils.createAssertBuilder();

        for (String fullField : ValidationTestUtils.extendFields(builder, CommonEntity.ID_FIELD_NAME, InsertPlanValidator.EXERCISES_FIELD)) {
            assertBuilder.addAssert(fullField, RestrictionCode.REQUIRED);
        }

        assertBuilder.assertion(errors);
    }


    private void validateWorkout(int phaseIndex, int workoutIndex, Errors errors) {
        ValidationTestUtils.FieldPathBuilder builder = ValidationTestUtils.createFieldPathBuilder()
                .addCollectionField(InsertPlanValidator.PHASES_FIELD, phaseIndex)
                .addCollectionField(InsertPlanValidator.WORKOUTS_FIELD, workoutIndex);

        ValidationTestUtils.AssertBuilder assertBuilder = ValidationTestUtils.createAssertBuilder();

        for (String fullField : ValidationTestUtils.extendFields(builder, Workout.MUSCLES_FIELD,Workout.POSITION_FIELD,
                Workout.WEEK_DAY_FIELD, InsertPlanValidator.GROUPS_FIELD)) {
            assertBuilder.addAssert(fullField, RestrictionCode.REQUIRED);
        }

        assertBuilder.assertion(errors);
    }

    private void validatePhase(int index, Errors errors) {
        ValidationTestUtils.FieldPathBuilder builder = ValidationTestUtils.createFieldPathBuilder()
                .addCollectionField(InsertPlanValidator.PHASES_FIELD, index);

        ValidationTestUtils.AssertBuilder assertBuilder = ValidationTestUtils.createAssertBuilder();

        for (String fullField : ValidationTestUtils.extendFields(builder, Phase.POSITION_FIELD, Phase.DESCRIPTION_FIELD,
                Phase.WEEKS_FIELD, Phase.GOAL_FIELD, InsertPlanValidator.WORKOUTS_FIELD)) {
            assertBuilder.addAssert(fullField, RestrictionCode.REQUIRED);
        }

        assertBuilder.assertion(errors);
    }
}