package pl.jakubpiecuch.gymhome.service.repository;

import pl.jakubpiecuch.gymhome.dao.impl.Criteria;

/**
 * Created by Rico on 2015-01-02.
 */
public interface Repository<T extends RepoObject, C extends Criteria> extends StorageRepository<T>, ReadRepository<T, C> {
}
