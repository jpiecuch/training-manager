package pl.jakubpiecuch.trainingmanager.service.user.workout.session.execution;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.repository.AbstractConversionRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.CommonReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.UpdatableRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.execution.ExecutionCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;

/**
 * Created by Rico on 2015-01-31.
 */
public class ExecutionRepository extends AbstractConversionRepository<ExecutionDto, Execution, ExecutionCriteria> {

    @Override
    public long create(ExecutionDto element) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void delete(Execution entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Execution getEmpty() {
        return new Execution();
    }
}
