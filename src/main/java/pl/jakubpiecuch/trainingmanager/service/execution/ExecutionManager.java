package pl.jakubpiecuch.trainingmanager.service.execution;

import pl.jakubpiecuch.trainingmanager.service.execution.session.SessionExecutionCriteria;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;

/**
 * Created by Rico on 2015-01-18.
 */
public interface ExecutionManager extends ReadRepository<ExecutionDto, SessionExecutionCriteria> {
}
