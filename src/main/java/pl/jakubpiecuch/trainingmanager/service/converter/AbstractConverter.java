package pl.jakubpiecuch.trainingmanager.service.converter;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.identify.IdentifyObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public T fromEntity(E entity) {
        Assert.notNull(entity);
        return convertTo(entity);
    }

    @Override
    public E toEntity(T object, E entity) {
        Assert.notNull(object);
        return convertFrom(object, entity != null ? entity : getEmpty());
    }

    protected abstract E convertFrom(T dto, E entity);
    protected abstract T convertTo(E entity);
    protected abstract E getEmpty();

    protected Map<Long, ? extends IdentifyObject> uniqueMap(List<? extends IdentifyObject> list) {
        return  Maps.uniqueIndex(Collections2.filter(list, new Predicate<IdentifyObject>() {
            @Override
            public boolean apply(IdentifyObject input) {
                return input.getId() != null;
            }
        }), new Function<IdentifyObject, Long>() {
            @Override
            public Long apply(IdentifyObject input) {
                return input.getId();
            }
        });
    }

    protected Collection<? extends IdentifyObject> filterNew(Collection<? extends IdentifyObject> collection) {
        return Collections2.filter(collection, new Predicate<IdentifyObject>() {
            @Override
            public boolean apply(IdentifyObject input) {
                return input.getId() == null;
            }
        });
    }
}
