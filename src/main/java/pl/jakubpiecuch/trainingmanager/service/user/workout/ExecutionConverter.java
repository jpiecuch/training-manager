package pl.jakubpiecuch.trainingmanager.service.user.workout;

import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;

/**
 * Created by Rico on 2015-01-18.
 */
public class ExecutionConverter extends AbstractConverter<ExecutionDto, Execution> {

    Converter exerciseConverter;

    @Override
    public ExecutionDto fromEntity(Execution entity, boolean full) {
        ExecutionDto result = new ExecutionDto();
        result.setSets(entity.getSets());
        result.setId(entity.getId());
        result.setComment(entity.getComment());
        result.setConfirm(entity.getConfirm());
        result.setExercise((ExerciseDto) exerciseConverter.fromEntity(entity.getExercise(), full));
        result.setWeights(entity.getWeights());
        return result;
    }

    @Override
    public Execution toEntity(ExecutionDto object) {
        throw new UnsupportedOperationException();
    }

    public void setExerciseConverter(Converter exerciseConverter) {
        this.exerciseConverter = exerciseConverter;
    }
}
