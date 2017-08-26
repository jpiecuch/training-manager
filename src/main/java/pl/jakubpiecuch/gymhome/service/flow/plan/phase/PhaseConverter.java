package pl.jakubpiecuch.gymhome.service.flow.plan.phase;

import pl.jakubpiecuch.gymhome.domain.Phase;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.domain.Workout;
import pl.jakubpiecuch.gymhome.service.converter.AbstractConverter;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.WorkoutConverter;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.gymhome.service.identify.IdentifyObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

        Map<Long, ? extends IdentifyObject> map = uniqueMap(dto.getWorkouts());

        List<Workout> workouts = new ArrayList<Workout>();
        for (Workout workout : entity.getWorkouts()) {
            if (map.containsKey(workout.getId())) {
                Workout e = workoutConverter.toEntity((WorkoutDto) map.get(workout.getId()), workout);
                e.setPhase(entity);
                workouts.add(e);
            }
        }

        Collection<? extends IdentifyObject> newWorkouts = filterNew(dto.getWorkouts());

        for (IdentifyObject workout : newWorkouts) {
            Workout e = workoutConverter.toEntity((WorkoutDto) workout, null);
            e.setPhase(entity);
            workouts.add(e);
        }

        entity.getWorkouts().clear();
        entity.getWorkouts().addAll(workouts);

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
