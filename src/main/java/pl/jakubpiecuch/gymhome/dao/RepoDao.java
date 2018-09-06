package pl.jakubpiecuch.gymhome.dao;

import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.domain.RepoCommonEntity;

import java.util.Map;

/**
 * Created by Rico on 2015-01-15.
 */
public interface RepoDao<E extends RepoCommonEntity, C extends Criteria> extends CoreDao<E> {
    PageResult<E> findByCriteria(C criteria);
    CountResult count(C criteria);
    Map<String, Long> group(C criteria);
}
