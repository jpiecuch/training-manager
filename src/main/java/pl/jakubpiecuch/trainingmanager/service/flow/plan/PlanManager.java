package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowManager;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowManager;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.GroupDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanManager extends AbstractFlowManager<PlanDto> implements ReadRepository<PlanDto, PlanCriteria> {

    private FlowManager<PhaseDto> phaseManager;
    private FlowManager<WorkoutDto> workoutManager;
    private FlowManager<ExerciseDto> exerciseManager;

    @Override
    public PageResult<PlanDto> read(PlanCriteria criteria) {
        RepoDao repoDao = (RepoDao) dao;
        final PageResult<Plan> result = repoDao.findByCriteria(criteria);

        return new PageResult<PlanDto>() {
            @Override
            public List<PlanDto> getResult() {
                return converter.fromEntityList(result.getResult(), false);
            }

            @Override
            public long getCount() {
                return result.getCount();
            }
        };
    }

    @Override
    public long save(PlanDto element) {
        element.setId(super.save(element));
        List<PhaseDto> phases = phaseManager.children(element.getId(), true);
        List<Long> touchedPhases = new ArrayList<Long>();
        for (PhaseDto phase : element.getPhases()) {
            phase.setPlanId(element.getId());
            phase.setId(phaseManager.save(phase));
            touchedPhases.add(phase.getId());
            List<WorkoutDto> workouts = workoutManager.children(phase.getId(), true);
            List<Long> touchedWorkouts = new ArrayList<Long>();
            for (WorkoutDto workout : phase.getWorkouts()) {
                workout.setPhaseId(phase.getId());
                workout.setId(workoutManager.save(workout));
                touchedWorkouts.add(workout.getId());
                List<ExerciseDto> exercises = exerciseManager.children(workout.getId(), false);
                List<Long> touchedExercises = new ArrayList<Long>();
                for (GroupDto group : workout.getGroups()) {
                    for (ExerciseDto exercise : group.getExercises()) {
                        exercise.setGroup(group.getId());
                        exercise.setWorkoutId(workout.getId());
                        exercise.setId(exerciseManager.save(exercise));
                        touchedExercises.add(exercise.getId());
                    }
                }
                for (ExerciseDto exercise : exercises) {
                    if (!touchedExercises.contains(exercise.getId())) {
                        exerciseManager.delete(exercise);
                    }
                }
            }
            for (WorkoutDto workout : workouts) {
                if (!touchedWorkouts.contains(workout.getId())) {
                    for (GroupDto group : workout.getGroups()) {
                        for (ExerciseDto exercise : group.getExercises()) {
                            exerciseManager.delete(exercise);
                        }
                    }
                    workoutManager.delete(workout);
                }
            }
        }
        for (PhaseDto phase : phases) {
            if (!touchedPhases.contains(phase.getId())) {
                for (WorkoutDto workout : phase.getWorkouts()) {
                    for (GroupDto group : workout.getGroups()) {
                        for (ExerciseDto exercise : group.getExercises()) {
                            exerciseManager.delete(exercise);
                        }
                    }
                    workoutManager.delete(workout);
                }
                phaseManager.delete(phase);
            }
        }
        return element.getId();
    }

    public void setExerciseManager(FlowManager<ExerciseDto> exerciseManager) {
        this.exerciseManager = exerciseManager;
    }

    public void setPhaseManager(FlowManager<PhaseDto> phaseManager) {
        this.phaseManager = phaseManager;
    }

    public void setWorkoutManager(FlowManager<WorkoutDto> workoutManager) {
        this.workoutManager = workoutManager;
    }
}
