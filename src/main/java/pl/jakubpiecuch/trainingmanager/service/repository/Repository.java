package pl.jakubpiecuch.trainingmanager.service.repository;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;

/**
 * Created by Rico on 2015-01-02.
 */
public interface Repository<T extends RepoObject, C extends Criteria> {
    PageResult<T> retrieve(C criteria);
    long create(T element);
    void update(T element);
    void delete(long id);
}
