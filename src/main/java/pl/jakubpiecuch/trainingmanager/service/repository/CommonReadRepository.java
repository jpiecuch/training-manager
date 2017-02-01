package pl.jakubpiecuch.trainingmanager.service.repository;

import pl.jakubpiecuch.trainingmanager.dao.CountResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.dao.util.DaoAssert;
import pl.jakubpiecuch.trainingmanager.domain.RepoCommonEntity;

import java.util.Map;

/**
 * Created by Rico on 2015-02-22.
 */
public class CommonReadRepository<E extends RepoCommonEntity, C extends Criteria> implements ReadRepository<E, C> {

    protected RepoDao<E, C> dao;

    @Override
    public PageResult<E> page(C criteria) {
        return dao.findByCriteria(criteria);
    }

    @Override
    public CountResult count(C criteria) {
        return dao.count(criteria);
    }

    @Override
    public Map<String, Long> group(C criteria) {
        return dao.group(criteria);
    }

    @Override
    public E unique(long id) {
        E entity = dao.findById(id);
        DaoAssert.notNull(entity);
        return entity;
    }

    public void setDao(RepoDao dao) {
        this.dao = dao;
    }
}
