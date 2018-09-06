package pl.jakubpiecuch.gymhome.service.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.dao.CountResult;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.dao.util.DaoAssert;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.RepoCommonEntity;
import pl.jakubpiecuch.gymhome.service.converter.Converter;
import pl.jakubpiecuch.gymhome.web.validator.ValidationType;

import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2015-02-22.
 */
public abstract class AbstractConversionRepository<T extends RepoObject, E extends RepoCommonEntity, C extends Criteria> implements Repository<T, C> {

    protected Converter<T, E> converter;
    protected Map<ValidationType, Validator> validators;
    protected RepoDao<E, C> dao;
    protected String name;

    @Override
    @Transactional
    public PageResult<T> page(C criteria) {
        final PageResult result = dao.findByCriteria(criteria);
        final List<T> entities = converter.fromEntities(result.getResult());
        return new PageResult<T>() {
            @Override
            public List<T> getResult() {
                return entities;
            }

            @Override
            public long getCount() {
                return result.getCount();
            }
        };
    }

    @Override
    public CountResult count(C criteria) {
        return dao.count(criteria);
    }

    @Override
    public Map<String, Long> group(C criteria) {
        return dao.group(criteria);
    }

    @Override
    public long create(T element) {
        validators.get(ValidationType.INSERT).validate(element, new BeanPropertyBindingResult(element, name));
        element.setId(dao.create(converter.toEntity(element, getEmpty())));
        return element.getId();
    }

    @Override
    public void update(T element) {
        CommonEntity entity = dao.findById(element.getId());
        DaoAssert.notNull(entity);
        validators.get(ValidationType.UPDATE).validate(element, new BeanPropertyBindingResult(element, name));
        dao.update(converter.toEntity(element, (E) entity));
    }

    @Override
    @Transactional
    public T unique(long id) {
        E entity = dao.findById(id);
        DaoAssert.notNull(entity);
        return converter.fromEntity(entity);
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

    public abstract E getEmpty();

    public void setConverter(Converter<T, E> converter) {
        this.converter = converter;
    }

    public void setDao(RepoDao<E, C> dao) {
        this.dao = dao;
    }

    public void setValidators(Map<ValidationType, Validator> validators) {
        this.validators = validators;
    }

    public void setName(String name) {
        this.name = name;
    }
}
