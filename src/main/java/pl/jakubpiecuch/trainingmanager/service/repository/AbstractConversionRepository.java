package pl.jakubpiecuch.trainingmanager.service.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;

import java.util.List;

/**
 * Created by Rico on 2015-02-22.
 */
public abstract class AbstractConversionRepository<T extends RepoObject, E extends RepoObject, C extends Criteria> extends CommonRepository<T,C> implements Repository<T, C> {

    protected Converter<T,E> converter;

    @Override
    @Transactional
    public PageResult<T> read(C criteria) {
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
        validator.validate(element, new BeanPropertyBindingResult(element, name));
        element.setId(dao.create((CommonEntity) converter.toEntity(element, getEmpty())));
        return element.getId();
    }

    @Override
    public void update(T element) {
        validator.validate(element, new BeanPropertyBindingResult(element, name));
        dao.update((CommonEntity) converter.toEntity(element, (E) dao.findById(element.getId())));
    }

    @Override
    public T retrieve(long id) {
        return converter.fromEntity((E) dao.findById(id));
    }

    public abstract E getEmpty();

    public void setConverter(Converter<T, E> converter) {
        this.converter = converter;
    }
}
