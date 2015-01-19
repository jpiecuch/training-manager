package pl.jakubpiecuch.trainingmanager.service.execution;

import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;

/**
 * Created by Rico on 2015-01-18.
 */
public class ExecutionConverter extends AbstractConverter<ExecutionDto, Execution> {

    @Override
    public ExecutionDto fromEntity(Execution entity, boolean full) {
        ExecutionDto result = new ExecutionDto();
        result.setSets(entity.getSets());
        result.setId(entity.getId());
        result.setAccountId(entity.getAccount().getId());
        result.setComment(entity.getComment());
        result.setConfirm(entity.getConfirm());
        result.setDate(entity.getDate());
        result.setExerciseId(entity.getExercise().getId());
        result.setWeights(entity.getWeights());
        return result;
    }

    @Override
    public Execution toEntity(ExecutionDto object) {
        throw new UnsupportedOperationException();
    }
}
