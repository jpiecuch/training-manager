package pl.jakubpiecuch.trainingmanager.service.repository;

/**
 * Created by Rico on 2015-01-31.
 */
public interface UpdatableRepository<T> {
    void update(T element);
}
