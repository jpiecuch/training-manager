package pl.jakubpiecuch.trainingmanager.service.repository;

/**
 * Created by Rico on 2015-01-15.
 */
public interface StorageRepository<T extends RepoObject> extends UpdateManager<T> {
    long create(T element);
    void delete(long id);
}
