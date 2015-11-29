package pl.jakubpiecuch.trainingmanager.dao.impl;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2015-02-21.
 */
public abstract class AbstractRepoDao<E extends CommonEntity, C extends Criteria> extends CoreDaoImpl<E> implements RepoDao<E, C> {

    @Override
    public PageResult<E> findByCriteria(C criteria) {

        final List<Object[]> result = criteria != null ? criteria.query(session()).list() : new ArrayList<Object[]>();

        return new PageResult<E>() {
            @Override
            public List<E> getResult() {
                return Lists.transform(result, new Function<Object[], E>() {
                    @Override
                    public E apply(Object[] in) {
                        return (E) in[0];
                    }
                });
            }

            @Override
            public long getCount() {
                return !result.isEmpty() ? (Long)result.get(0)[1] : 0;
            }
        };
    }
}
