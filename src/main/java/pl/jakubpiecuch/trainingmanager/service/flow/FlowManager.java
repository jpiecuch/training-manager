package pl.jakubpiecuch.trainingmanager.service.flow;

public interface FlowManager<T extends Flow> {
    T retrieve(long id);
    long create(T element);
}
