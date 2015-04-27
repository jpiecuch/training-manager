package pl.jakubpiecuch.trainingmanager.service.user.workout;

import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;

/**
 * Created by Rico on 2015-01-18.
 */
public class ExecutionConverter extends AbstractConverter<ExecutionDto, Execution> {

    private Converter exerciseConverter;
    private ExecutionDao executionDao;

    @Override
    public ExecutionDto fromEntity(Execution entity, boolean full) {
        ExecutionDto result = new ExecutionDto();
        result.setId(entity.getId());
        result.setComment(entity.getComment());
        result.setExercise((ExerciseDto) exerciseConverter.fromEntity(entity.getExercise(), full));
        result.setState(entity.getState());
        result.setResults(entity.getResults());
        return result;
    }

    @Override
    public Execution toEntity(ExecutionDto object) {
        Execution execution = executionDao.findById(object.getId());
        execution.setComment(object.getComment());
        execution.setState(object.getState());
        execution.setResults(object.getResults());
        return execution;
    }

    public void setExerciseConverter(Converter exerciseConverter) {
        this.exerciseConverter = exerciseConverter;
    }

    public void setExecutionDao(ExecutionDao executionDao) {
        this.executionDao = executionDao;
    }
}
