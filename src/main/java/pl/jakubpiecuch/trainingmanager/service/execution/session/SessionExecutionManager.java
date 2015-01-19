package pl.jakubpiecuch.trainingmanager.service.execution.session;

import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.execution.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.service.execution.ExecutionManager;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public class SessionExecutionManager implements ExecutionManager {

    ExecutionDao executionDao;
    Converter converter;

    @Override
    public PageResult<ExecutionDto> read(SessionExecutionCriteria criteria) {
        final PageResult result = ((RepoDao) executionDao).findByCriteria(criteria);
        return new PageResult<ExecutionDto>() {
            @Override
            public List<ExecutionDto> getResult() {
                return converter.fromEntityList(result.getResult(), false);
            }

            @Override
            public long getCount() {
                return result.getCount();
            }
        };
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void setExecutionDao(ExecutionDao executionDao) {
        this.executionDao = executionDao;
    }
}
