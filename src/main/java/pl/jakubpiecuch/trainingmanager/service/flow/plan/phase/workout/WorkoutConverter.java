package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout;

import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseManager;

/**
 * Created by Rico on 2014-12-31.
 */
public class WorkoutConverter extends AbstractFlowConverter<WorkoutDto, Workout> {

    @Override
    protected Workout convertFrom(WorkoutDto flowObject) throws Exception {
        Workout entity = new Workout();

        entity.setId(flowObject.getId());
        entity.setMuscles(flowObject.getMuscles());
        entity.setPhase(new Phase(flowObject.getPhaseId()));
        entity.setPosition(flowObject.getPosition());
        entity.setWeekDay(flowObject.getWeekDay());

        return entity;
    }

    @Override
    protected WorkoutDto convertTo(Workout entity, boolean full) {
        WorkoutDto dto = new WorkoutDto();

        dto.setId(entity.getId());
        dto.setPosition(entity.getPosition());
        dto.setWeekDay(entity.getWeekDay());
        dto.setMuscles(entity.getMuscles());
        dto.setPhaseId(entity.getPhase().getId());
        dto.setExercises(full ? manager.children(entity.getId(), full) : null);

        return dto;
    }
}
