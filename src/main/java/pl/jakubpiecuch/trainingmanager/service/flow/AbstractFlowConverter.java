package pl.jakubpiecuch.trainingmanager.service.flow;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowConverter<T extends Flow, E extends CommonEntity> implements FlowConverter<T, E> {

    protected FlowManager manager;

    @Override
    public List<T> toFlowObjectList(List<E> list, final boolean full) {
        return Lists.transform(list, new Function<E, T>() {

            @Override
            public T apply(E input) {
                return toFlowObject(input, full);
            }
        });
    }

    @Override
    public List<E> fromFlowObjectList(List<T> list) {
        return Lists.transform(list, new Function<T, E>() {

            @Override
            public E apply(T input) {
                try {
                    return fromFlowObject(input);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    @Override
    public T toFlowObject(E entity, boolean full) {
        Assert.notNull(entity);
        return convertTo(entity, full);
    }

    @Override
    public E fromFlowObject(T flowObject) throws Exception {
        Assert.notNull(flowObject);
        return convertFrom(flowObject);
    }

    public void setManager(FlowManager manager) {
        this.manager = manager;
    }

    protected abstract E convertFrom(T flowObject) throws Exception;
    protected abstract T convertTo(E entity, boolean full);
}
