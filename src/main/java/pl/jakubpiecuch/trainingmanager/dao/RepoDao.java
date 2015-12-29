package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;

/**
 * Created by Rico on 2015-01-15.
 */
public interface RepoDao<E extends CommonEntity, C extends Criteria> extends CoreDao<E> {
    PageResult<E> findByCriteria(C criteria);
}
