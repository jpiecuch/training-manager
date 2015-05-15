package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase;

import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutConverter;

import java.util.ArrayList;

/**
 * Created by Rico on 2014-12-31.
 */
public class PhaseConverter extends AbstractConverter<PhaseDto, Phase> {

    private WorkoutConverter workoutConverter;

    @Override
    protected PhaseDto convertTo(Phase entity) {

        PhaseDto flow = new PhaseDto();
        flow.setId(entity.getId());
        flow.setDescription(entity.getDescription());
        flow.setGoal(entity.getGoal());
        flow.setPlanId(entity.getPlan().getId());
        flow.setPosition(entity.getPosition());
        flow.setWeeks(entity.getWeeks());
        flow.setWorkouts(workoutConverter.fromEntities(entity.getWorkouts()));
        return flow;
    }

    @Override
    protected Phase convertFrom(PhaseDto dto, Phase entity) {
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setGoal(dto.getGoal());
        entity.setPlan(new Plan(dto.getPlanId()));
        entity.setPosition(dto.getPosition());
        entity.setWeeks(dto.getWeeks());

        entity.setWorkouts(new ArrayList<Workout>());
        for (Workout workout : workoutConverter.toEntities(dto.getWorkouts(), entity.getWorkouts())) {
            workout.setPhase(entity);
            entity.getWorkouts().add(workout);
        }

        return entity;
    }

    @Override
    protected Phase getEmpty() {
        return new Phase();
    }

    public void setWorkoutConverter(WorkoutConverter workoutConverter) {
        this.workoutConverter = workoutConverter;
    }
}
