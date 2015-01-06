package pl.jakubpiecuch.trainingmanager.service.flow;

import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public interface FlowConverter<T extends Flow, E extends CommonEntity> {
    T toFlowObject(E entity, boolean full);
    List<T> toFlowObjectList(List<E> list, boolean full);
    E fromFlowObject(T flowObject) throws Exception;
    List<E> fromFlowObjectList(List<T> list);
}
