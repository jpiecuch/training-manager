package pl.jakubpiecuch.trainingmanager.service.repository;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;

/**
 * Created by Rico on 2015-01-15.
 */
public interface ReadRepository<T extends RepoObject, C extends Criteria>  {
    PageResult<T> read(C criteria);
}
