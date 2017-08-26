package pl.jakubpiecuch.gymhome.service.user.workout.session.execution;

import pl.jakubpiecuch.gymhome.domain.Execution;
import pl.jakubpiecuch.gymhome.service.repository.AbstractConversionRepository;
import pl.jakubpiecuch.gymhome.service.repository.execution.ExecutionCriteria;
import pl.jakubpiecuch.gymhome.service.user.workout.ExecutionDto;

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
