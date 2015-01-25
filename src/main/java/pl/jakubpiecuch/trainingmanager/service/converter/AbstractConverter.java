package pl.jakubpiecuch.trainingmanager.service.converter;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public abstract class AbstractConverter<T, E extends CommonEntity> implements Converter<T, E> {

    @Override
    public List<T> fromEntityList(List<E> list, final boolean full) {
        Assert.notNull(list);
        return Lists.newArrayList(Lists.transform(list, new Function<E, T>() {

            @Override
            public T apply(E input) {
                return fromEntity(input, full);
            }
        }));
    }

    @Override
    public List<E> toEntityList(List<T> list) {
        Assert.notNull(list);
        return Lists.newArrayList(Lists.transform(list, new Function<T, E>() {

            @Override
            public E apply(T input) {
                    return toEntity(input);
            }
        }));
    }
}
