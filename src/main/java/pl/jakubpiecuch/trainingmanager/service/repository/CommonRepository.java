package pl.jakubpiecuch.trainingmanager.service.repository;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

/**
 * Created by Rico on 2015-02-22.
 */
public class CommonRepository<E extends RepoObject, C extends Criteria> extends CommonReadRepository<E, C> implements Repository<E, C> {

    protected Validator validator;
    protected String name;

    @Override
    public long create(E element) {
        validator.validate(element, new BeanPropertyBindingResult(element, name));
        dao.create((CommonEntity) element);
        return element.getId();
    }

    @Override
    public void update(E element) {
        validator.validate(element, new BeanPropertyBindingResult(element, name));
        dao.update((CommonEntity) element);
    }

    @Override
    public void delete(long id) {
        dao.delete(new CommonEntity(id));
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setName(String name) {
        this.name = name;
    }
}
