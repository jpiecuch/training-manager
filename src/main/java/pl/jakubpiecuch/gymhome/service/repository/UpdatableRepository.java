package pl.jakubpiecuch.gymhome.service.repository;

/**
 * Created by Rico on 2015-01-31.
 */
public interface UpdatableRepository<T extends RepoObject> {
    void update(T element);
}
