package pl.jakubpiecuch.trainingmanager.service.user.plan;

import org.joda.time.DateTime;
import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;
import pl.jakubpiecuch.trainingmanager.domain.*;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowManager;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rico on 2015-01-22.
 */
public class SessionUserPlanStarter implements UserPlanStarter {

    private Map<Integer, Integer> dayOfWeekMapper = new HashMap<Integer, Integer>();
    private FlowManager<PlanDto> manager;
    private ExecutionDao executionDao;
    private UserWorkoutDao userWorkoutDao;
    private PlanDao planDao;

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
                    dateTime = dateTime.withWeekOfWeekyear(currentWeek).withDayOfWeek(dayOfWeekMapper.get(workout.getWeekDay().ordinal()));
                    UserWorkout userWorkout = new UserWorkout();
                    userWorkout.setDate(dateTime.toDate());
                    userWorkout.setWorkout(new Workout(workout.getId()));
                    userWorkout.setAccount(AuthenticatedUserUtil.getUser());
                    userWorkoutDao.create(userWorkout);
                    for (GroupDto group : workout.getGroups()) {
                        for(ExerciseDto exercise : group.getExercises()) {
                            Execution execution = new Execution();
                            execution.setExercise(new Exercise(exercise.getId()));
                            execution.setWorkout(userWorkout);
                            executionDao.create(execution);
                        }
                    }
                }
                weekIncrease++;
            }
        }
        Plan entity = planDao.findById(userPlan.getPlanId());
        entity.setUsed(true);
        planDao.update(entity);
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

    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }
    
    private void afterPropertiesSet() {
        dayOfWeekMapper.put(0, 7);
        dayOfWeekMapper.put(1, 1);
        dayOfWeekMapper.put(2, 2);
        dayOfWeekMapper.put(3, 3);
        dayOfWeekMapper.put(4, 4);
        dayOfWeekMapper.put(5, 5);
        dayOfWeekMapper.put(6, 6);
    }
}
