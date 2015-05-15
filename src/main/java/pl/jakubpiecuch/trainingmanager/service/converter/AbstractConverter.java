package pl.jakubpiecuch.trainingmanager.service.converter;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.identify.IdentifyObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public abstract class AbstractConverter<T extends IdentifyObject, E extends CommonEntity> implements Converter<T, E> {

    @Override
    public List<T> fromEntities(List<E> list) {
        Assert.notNull(list);
        return Lists.newArrayList(Lists.transform(list, new Function<E, T>() {

            @Override
            public T apply(E input) {
                return fromEntity(input);
            }
        }));
    }

    @Override
    public List<E> toEntities(List<T> list, final List<E> entities) {
        Assert.notNull(list);
        return Lists.newArrayList(Lists.transform(list, new Function<T, E>() {

            @Override
            public E apply(final T input) {

                Iterator<E> iterator = Iterables.filter(entities, new Predicate<E>() {
                    @Override
                    public boolean apply(E e) {
                        return e.getId() == input.getId();
                    }
                }).iterator();

                return toEntity(input, input.getId() != null && iterator.hasNext() ? iterator.next() : getEmpty());
            }
        }));
    }

    @Override
    public T fromEntity(E entity) {
        Assert.notNull(entity);
        return convertTo(entity);
    }

    @Override
    public E toEntity(T object, E entity) {
        Assert.notNull(object);
        return convertFrom(object, entity);
    }

    protected abstract E convertFrom(T dto, E entity);
    protected abstract T convertTo(E entity);
    protected abstract E getEmpty();
}
