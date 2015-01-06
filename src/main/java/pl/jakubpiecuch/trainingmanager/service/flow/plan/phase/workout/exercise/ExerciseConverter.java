package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;

/**
 * Created by Rico on 2014-12-31.
 */
public class ExerciseConverter extends AbstractFlowConverter<ExerciseDto, Exercise> {

    @Override
    protected Exercise convertFrom(ExerciseDto flowObject) throws Exception {
        Exercise entity = new Exercise();

        entity.setId(flowObject.getId());
        entity.setDescription(new Description(flowObject.getDescriptionId()));
        entity.setWorkout(new Workout(flowObject.getWorkoutId()));
        entity.setSets(flowObject.getSets());

        return entity;
    }

    @Override
    protected ExerciseDto convertTo(Exercise entity, boolean full) {
        ExerciseDto dto = new ExerciseDto();

        dto.setId(entity.getId());
        dto.setSets(entity.getSets());
        dto.setDescriptionId(entity.getDescription().getId());
        dto.setWorkoutId(entity.getWorkout().getId());
        dto.setDescription(full ? entity.getDescription() : null);
        return dto;
    }

}
