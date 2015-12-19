package pl.jakubpiecuch.trainingmanager.service.user.workout.session.execution;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.repository.UpdatableRepository;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;

/**
 * Created by Rico on 2015-01-31.
 */
public class ExecutionUpdatableRepository implements UpdatableRepository<ExecutionDto> {

    private Converter<ExecutionDto, Execution> converter;
    private Validator validator;
    private CoreDao<Execution> executionDao;

    @Override
    public void update(ExecutionDto element) {
        validator.validate(element, new BeanPropertyBindingResult(element, "execution"));
        executionDao.update(converter.toEntity(element, executionDao.findById(element.getId())));
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void setExecutionDao(CoreDao<Execution> executionDao) {
        this.executionDao = executionDao;
    }
}
