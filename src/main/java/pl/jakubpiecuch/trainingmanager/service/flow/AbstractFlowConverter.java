package pl.jakubpiecuch.trainingmanager.service.flow;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowConverter<T extends Flow, E extends CommonEntity> extends AbstractConverter<T, E> {

    protected FlowManager manager;

    @Override
    public T fromEntity(E entity, boolean full) {
        Assert.notNull(entity);
        return convertTo(entity, full);
    }

    @Override
    public E toEntity(T object) {
        Assert.notNull(object);
        return convertFrom(object);
    }

    public void setManager(FlowManager manager) {
        this.manager = manager;
    }

    protected abstract E convertFrom(T flowObject);
    protected abstract T convertTo(E entity, boolean full);
}
