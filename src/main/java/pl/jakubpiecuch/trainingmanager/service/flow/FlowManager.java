package pl.jakubpiecuch.trainingmanager.service.flow;

import java.util.List;

public interface FlowManager<T extends Flow> {
    T retrieve(long id);
    long create(T element);
    List<T> children(long parentId);
}
