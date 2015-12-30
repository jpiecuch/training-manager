package pl.jakubpiecuch.trainingmanager.service.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.dao.util.DaoAssert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.web.validator.ValidationType;

import java.util.List;

/**
 * Created by Rico on 2015-02-22.
 */
public abstract class AbstractConversionRepository<T extends RepoObject, E extends RepoObject, C extends Criteria> extends CommonRepository<T, C> {

    protected Converter<T, E> converter;

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
    public long create(T element) {
        validators.get(ValidationType.INSERT).validate(element, new BeanPropertyBindingResult(element, name));
        element.setId(dao.create((CommonEntity) converter.toEntity(element, getEmpty())));
        return element.getId();
    }

    @Override
    public void update(T element) {
        CommonEntity entity = dao.findById(element.getId());
        DaoAssert.notNull(entity);
        validators.get(ValidationType.UPDATE).validate(element, new BeanPropertyBindingResult(element, name));
        dao.update((CommonEntity) converter.toEntity(element, (E) entity));
    }

    @Override
    @Transactional
    public T unique(long id) {
        return converter.fromEntity((E) super.unique(id));
    }

    public abstract E getEmpty();

    public void setConverter(Converter<T, E> converter) {
        this.converter = converter;
    }
}
