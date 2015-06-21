package pl.jakubpiecuch.trainingmanager.service.repository;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;

import java.util.List;

/**
 * Created by Rico on 2015-02-22.
 */
public abstract class AbstractConversionRepository<T extends RepoObject, E extends RepoObject, C extends Criteria> extends AbstractRepository<T,C> implements Repository<T, C> {

    protected Converter<T,E> converter;

    @Override
    public PageResult<T> read(C criteria) {
        final PageResult entities = dao.findByCriteria(criteria);
        return new PageResult<T>() {
            @Override
            public List<T> getResult() {
                return converter.fromEntities(entities.getResult());
            }

            @Override
            public long getCount() {
                return entities.getCount();
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
        dao.update((CommonEntity) element);
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
