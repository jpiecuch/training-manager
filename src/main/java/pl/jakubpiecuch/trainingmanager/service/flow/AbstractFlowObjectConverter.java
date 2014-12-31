package pl.jakubpiecuch.trainingmanager.service.flow;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowObjectConverter<T extends FlowObject, E extends CommonEntity> implements FlowObjectConverter<T, E> {

    @Override
    public List<T> toFlowObjectList(List<E> list) {
        return Lists.transform(list, new Function<E, T>() {

            @Override
            public T apply(E input) {
                return toFlowObject(input);
            }
        });
    }

    @Override
    public List<E> fromFlowObjectList(List<T> list) {
        return Lists.transform(list, new Function<T, E>() {

            @Override
            public E apply(T input) {
                return fromFlowObject(input);
            }
        });
    }

    @Override
    public T toFlowObject(E entity) {
        Assert.notNull(entity);
        return convertTo(entity);
    }

    @Override
    public E fromFlowObject(T flowObject) {
        Assert.notNull(flowObject);
        return convertFrom(flowObject);
    }

    protected abstract E convertFrom(T flowObject);
    protected abstract T convertTo(E entity);
}
