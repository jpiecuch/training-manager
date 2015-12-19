package pl.jakubpiecuch.trainingmanager.service.user.workout;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;

/**
 * Created by Rico on 2015-01-18.
 */
public class ExecutionConverter extends AbstractConverter<ExecutionDto, Execution> {

    private Converter exerciseConverter;
    private CoreDao<Execution> executionDao;

    @Override
    protected ExecutionDto convertTo(Execution entity) {
        ExecutionDto result = new ExecutionDto();
        result.setId(entity.getId());
        result.setComment(entity.getComment());
        result.setExercise((ExerciseDto) exerciseConverter.fromEntity(entity.getExercise()));
        result.setState(entity.getState());
        result.setResults(entity.getResults());
        return result;
    }

    @Override
    protected Execution convertFrom(ExecutionDto object, Execution entity) {
        Execution execution = executionDao.findById(object.getId());
        execution.setComment(object.getComment());
        execution.setState(object.getState());
        execution.setResults(object.getResults());
        return execution;
    }

    @Override
    protected Execution getEmpty() {
        return new Execution();
    }

    public void setExerciseConverter(Converter exerciseConverter) {
        this.exerciseConverter = exerciseConverter;
    }

    public void setExecutionDao(CoreDao<Execution> executionDao) {
        this.executionDao = executionDao;
    }
}
