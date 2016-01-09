package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.RepoCommonEntity;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

/**
 * Created by Rico on 2015-01-15.
 */
public interface RepoDao<E extends RepoCommonEntity, C extends Criteria> extends CoreDao<E> {
    PageResult<E> findByCriteria(C criteria);
}
