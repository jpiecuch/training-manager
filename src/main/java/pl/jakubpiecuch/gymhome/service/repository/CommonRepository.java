package pl.jakubpiecuch.gymhome.service.repository;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.dao.util.DaoAssert;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.RepoCommonEntity;
import pl.jakubpiecuch.gymhome.web.validator.ValidationType;

import java.util.Map;

/**
 * Created by Rico on 2015-02-22.
 */
public class CommonRepository<E extends RepoCommonEntity, C extends Criteria> extends CommonReadRepository<E, C> implements Repository<E, C> {

    protected Map<ValidationType, Validator> validators;
    protected String name;

    @Override
    public long create(E element) {
        validators.get(ValidationType.INSERT).validate(element, new BeanPropertyBindingResult(element, name));
        dao.create(element);
        return element.getId();
    }

    @Override
    public void update(E element) {
        CommonEntity entity = dao.findById(element.getId());
        DaoAssert.notNull(entity);
        validators.get(ValidationType.UPDATE).validate(element, new BeanPropertyBindingResult(element, name));
        dao.update(element);
    }

    @Override
    public void delete(long id) {
        E entity = dao.findById(id);
        DaoAssert.notNull(entity);
        delete(entity);
    }

    protected void delete(E entity) {
        dao.delete(entity);
    }

    public void setValidators(Map<ValidationType, Validator> validators) {
        this.validators = validators;
    }

    public void setName(String name) {
        this.name = name;
    }
}
