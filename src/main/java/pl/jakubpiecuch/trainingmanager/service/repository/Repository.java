package pl.jakubpiecuch.trainingmanager.service.repository;

/**
 * Created by Rico on 2015-01-02.
 */
public interface Repository<T extends RepoObject, C extends Criteria> extends StorageRepository<T>, ReadRepository<T,C> {
}
