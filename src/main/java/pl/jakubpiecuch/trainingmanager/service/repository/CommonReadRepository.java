package pl.jakubpiecuch.trainingmanager.service.repository;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.util.DaoAssert;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

/**
 * Created by Rico on 2015-02-22.
 */
public class CommonReadRepository<E extends RepoObject, C extends Criteria> implements ReadRepository<E, C> {

    protected RepoDao dao;

    @Override
    public PageResult<E> read(C criteria) {
        return dao.findByCriteria(criteria);
    }

    @Override
    public E retrieve(long id) {
        CommonEntity entity = dao.findById(id);
        DaoAssert.notNull(entity);
        return (E) entity;
    }

    public void setDao(RepoDao dao) {
        this.dao = dao;
    }
}
