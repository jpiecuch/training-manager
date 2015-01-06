package pl.jakubpiecuch.trainingmanager.service.flow;

import java.util.List;

public interface FlowManager<T extends Flow> {
    T retrieve(long id, boolean full);
    long create(T element) throws Exception;
    List<T> children(long parentId, boolean full);
}
