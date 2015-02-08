package pl.jakubpiecuch.trainingmanager.service.user.plan;

import org.joda.time.DateTime;
import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowManager;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rico on 2015-01-22.
 */
public class SessionUserPlanStarter implements UserPlanStarter {

    private static final Map<Integer, Integer> DAY_OF_WEEK_MAPPER = new HashMap<Integer, Integer>() {
        {
            put(0, 7);put(1, 1);put(2, 2);put(3,3);put(4,4);put(5,5);put(6,6);
        }
    };

    private FlowManager<PlanDto> manager;
    private ExecutionDao executionDao;
    private UserWorkoutDao userWorkoutDao;

    @Override
    public void start(UserPlan userPlan) {
        PlanDto plan = manager.retrieve(userPlan.getPlanId(), true);
        int weekIncrease = 0;
        for (PhaseDto phase : plan.getPhases()) {
            for (int i = 0; i < phase.getWeeks(); i++) {
                for (WorkoutDto workout : phase.getWorkouts()) {
                    DateTime dateTime = new DateTime().withYear(userPlan.getYear());
                    int currentWeek = userPlan.getWeek() + weekIncrease;
                    if (dateTime.weekOfWeekyear().getMaximumValue() <  currentWeek) {
                        dateTime = dateTime.plusYears(1);
                        currentWeek = currentWeek - dateTime.weekOfWeekyear().getMaximumValue();
                    }
                    dateTime = dateTime.withWeekOfWeekyear(currentWeek).withDayOfWeek(DAY_OF_WEEK_MAPPER.get(workout.getWeekDay().ordinal()));
                    UserWorkout userWorkout = new UserWorkout();
                    userWorkout.setDate(dateTime.toDate());
                    userWorkout.setWorkout(new Workout(workout.getId()));
                    userWorkout.setAccount(AuthenticatedUserUtil.getUser());
                    userWorkoutDao.save(userWorkout);
                    for (ExerciseDto exercise : workout.getExercises()) {
                        Execution execution = new Execution();
                        execution.setExercise(new Exercise(exercise.getId()));
                        execution.setWorkout(userWorkout);
                        executionDao.save(execution);
                    }
                }
                weekIncrease++;
            }
        }
        plan.setUsed(true);
        manager.save(plan);
    }

    public void setExecutionDao(ExecutionDao executionDao) {
        this.executionDao = executionDao;
    }

    public void setUserWorkoutDao(UserWorkoutDao userWorkoutDao) {
        this.userWorkoutDao = userWorkoutDao;
    }

    public void setManager(FlowManager<PlanDto> manager) {
        this.manager = manager;
    }
}
