package pl.jakubpiecuch.gymhome.service.repository;

import pl.jakubpiecuch.gymhome.dao.CountResult;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;

import java.util.Map;

/**
 * Created by Rico on 2015-01-15.
 */
public interface ReadRepository<T extends RepoObject, C extends Criteria> {
    PageResult<T> page(C criteria);
    CountResult count(C criteria);
    Map<String, Long> group(C criteria);
    T unique(long id);
}
