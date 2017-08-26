package pl.jakubpiecuch.gymhome.service.flow.plan.phase.workout.exercise;

import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.domain.Exercise;
import pl.jakubpiecuch.gymhome.domain.Workout;
import pl.jakubpiecuch.gymhome.service.converter.AbstractConverter;

/**
 * Created by Rico on 2014-12-31.
 */
public class ExerciseConverter extends AbstractConverter<ExerciseDto, Exercise> {

    @Override
    protected Exercise convertFrom(ExerciseDto dto, Exercise entity) {
        entity.setId(dto.getId());
        entity.setDescription(new Description(dto.getDescriptionId()));
        entity.setWorkout(new Workout(dto.getWorkoutId()));
        entity.setSets(dto.getSets());
        entity.setPosition(dto.getPosition());
        entity.setGroup(dto.getGroup());

        return entity;
    }

    @Override
    protected ExerciseDto convertTo(Exercise entity) {
        ExerciseDto dto = new ExerciseDto();

        dto.setId(entity.getId());
        dto.setSets(entity.getSets());
        dto.setDescriptionId(entity.getDescription().getId());
        dto.setWorkoutId(entity.getWorkout().getId());
        dto.setGroup(entity.getGroup());
        dto.setPosition(entity.getPosition());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    @Override
    protected Exercise getEmpty() {
        return new Exercise();
    }
}
