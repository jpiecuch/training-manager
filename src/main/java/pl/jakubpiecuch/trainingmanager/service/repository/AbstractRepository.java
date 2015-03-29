package pl.jakubpiecuch.trainingmanager.service.repository;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Description;

/**
 * Created by Rico on 2015-02-22.
 */
public abstract class AbstractRepository<E extends RepoObject, C extends Criteria> implements Repository<E, C> {

    protected RepoDao dao;
    private Validator validator;
    private String name;

    @Override
    public PageResult<E> read(C criteria) {
        return dao.findByCriteria(criteria);
    }

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
        dao.delete(new Description(id));
    }

    public void setDao(RepoDao dao) {
        this.dao = dao;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setName(String name) {
        this.name = name;
    }
}
