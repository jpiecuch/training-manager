package pl.jakubpiecuch.trainingmanager.service.flow;

import java.util.List;

public interface FlowManager<T extends Flow> {
    T retrieve(long id, boolean full);
    long save(T element);
    List<T> children(long parentId, boolean full);
}
