package pl.jakubpiecuch.trainingmanager.service.flow;

import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public interface FlowConverter<T extends Flow, E extends CommonEntity> {
    T toFlowObject(E entity);
    List<T> toFlowObjectList(List<E> list);
    E fromFlowObject(T flowObject);
    List<E> fromFlowObjectList(List<T> list);
}
