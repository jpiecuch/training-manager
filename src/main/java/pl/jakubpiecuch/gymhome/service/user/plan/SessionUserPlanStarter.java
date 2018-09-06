package pl.jakubpiecuch.gymhome.service.user.plan;

import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.domain.*;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanCriteria;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.gymhome.service.repository.Repository;
import pl.jakubpiecuch.gymhome.service.user.workout.session.UserWorkoutCriteria;
import pl.jakubpiecuch.gymhome.web.util.AuthenticatedUserUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rico on 2015-01-22.
 */
public class SessionUserPlanStarter implements UserPlanStarter {

    private Repository<PlanDto, PlanCriteria> planRepository;
    private CoreDao<Execution> executionDao;
    private RepoDao<UserWorkout, UserWorkoutCriteria> userWorkoutDao;

    @Override
    public void start(long planId, int year, int week) {
        PlanDto plan = planRepository.unique(planId);
        int weekIncrease = 0;
        for (PhaseDto phase : plan.getPhases()) {
            for (int i = 0; i < phase.getWeeks(); i++) {
                for (WorkoutDto workout : phase.getWorkouts()) {
                    createUserWorkout(year, week, weekIncrease, workout);
                }
                weekIncrease++;
            }
        }
        plan.setUsed(true);
        planRepository.update(plan);
    }

    private void createUserWorkout(int year, int week, int weekIncrease, WorkoutDto workout) {
        UserWorkout userWorkout = new UserWorkout();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        userWorkout.setDate(Date.from(Instant.from(LocalDate.of(year, 1,1).with(weekFields.weekOfYear(), week).with(weekFields.dayOfWeek(), workout.getWeekDay().getDayInWeek()).plusWeeks(weekIncrease).atStartOfDay(ZoneId.systemDefault()))));
        userWorkout.setWorkout(new Workout(workout.getId()));
        userWorkout.setAccount(AuthenticatedUserUtil.getUser());
        userWorkout.setState(UserWorkout.State.PLANNED);
        userWorkoutDao.create(userWorkout);
        for (GroupDto group : workout.getGroups()) {
            for (ExerciseDto exercise : group.getExercises()) {
                createExecution(userWorkout, exercise);
            }
        }
    }

    private void createExecution(UserWorkout userWorkout, ExerciseDto exercise) {
        Execution execution = new Execution();
        execution.setExercise(new Exercise(exercise.getId()));
        execution.setWorkout(userWorkout);
        execution.setState(UserWorkout.State.PLANNED);
        List<Execution.Result> results = new ArrayList<>();
        if (exercise.getDescription().getLateral() == Description.Lateral.BILATERAL) {
            results.add(new Execution.Result());
        } else {
            Execution.Result leftResult = new Execution.Result();
            leftResult.setSide(Execution.Result.LEFT_SIDE_CODE);
            results.add(leftResult);

            Execution.Result rightResult = new Execution.Result();
            rightResult.setSide(Execution.Result.RIGHT_SIDE_CODE);
            results.add(rightResult);
        }
        execution.setResults(results);
        executionDao.create(execution);
    }

    public void setExecutionDao(CoreDao<Execution> executionDao) {
        this.executionDao = executionDao;
    }

    public void setUserWorkoutDao(RepoDao<UserWorkout, UserWorkoutCriteria> userWorkoutDao) {
        this.userWorkoutDao = userWorkoutDao;
    }

    public void setPlanRepository(Repository<PlanDto, PlanCriteria> planRepository) {
        this.planRepository = planRepository;
    }
}
