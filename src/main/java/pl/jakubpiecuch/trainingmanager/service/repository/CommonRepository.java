package pl.jakubpiecuch.trainingmanager.service.repository;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.dao.util.DaoAssert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.web.validator.ValidationType;

import java.util.Map;

/**
 * Created by Rico on 2015-02-22.
 */
public class CommonRepository<E extends RepoObject, C extends Criteria> extends CommonReadRepository<E, C> implements Repository<E, C> {

    protected Map<ValidationType, Validator> validators;
    protected String name;

    @Override
    public long create(E element) {
        validators.get(ValidationType.INSERT).validate(element, new BeanPropertyBindingResult(element, name));
        dao.create((CommonEntity) element);
        return element.getId();
    }

    @Override
    public void update(E element) {
        CommonEntity entity = dao.findById(element.getId());
        DaoAssert.notNull(entity);
        validators.get(ValidationType.UPDATE).validate(element, new BeanPropertyBindingResult(element, name));
        dao.update((CommonEntity) element);
    }

    @Override
    public void delete(long id) {
        CommonEntity entity = dao.findById(id);
        DaoAssert.notNull(entity);
        delete(entity);
    }

    protected void delete(CommonEntity entity) {
        dao.delete(entity);
    }

    public void setValidators(Map<ValidationType, Validator> validators) {
        this.validators = validators;
    }

    public void setName(String name) {
        this.name = name;
    }
}
