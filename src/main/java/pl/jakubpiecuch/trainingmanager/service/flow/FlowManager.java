package pl.jakubpiecuch.trainingmanager.service.flow;

public interface FlowManager<T extends FlowObject> {
    T getElement(long id);
}
