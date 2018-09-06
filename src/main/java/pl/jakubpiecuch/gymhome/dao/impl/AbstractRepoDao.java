package pl.jakubpiecuch.gymhome.dao.impl;


import org.apache.commons.collections.MapUtils;
import pl.jakubpiecuch.gymhome.dao.CountResult;
import pl.jakubpiecuch.gymhome.dao.EmptyPageResult;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.gymhome.domain.RepoCommonEntity;
import pl.jakubpiecuch.gymhome.service.resolver.OrderResolver;

import java.util.Map;

/**
 * Created by Rico on 2015-02-21.
 */
public abstract class AbstractRepoDao<E extends RepoCommonEntity, C extends Criteria> extends CoreDaoImpl<E> implements RepoDao<E, C> {

    private Map<String, OrderResolver> orderResolvers;

    @Override
    public PageResult<E> findByCriteria(C criteria) {
       return criteria != null ? criteria.appendOrderResolvers(orderResolvers).page(session()) : new EmptyPageResult<>();
    }

    @Override
    public CountResult count(C criteria) {
        long count = criteria != null ? criteria.count(session()) : 0;
        return () -> count;
    }

    @Override
    public Map<String, Long> group(C criteria) {
        return criteria != null ? criteria.group(session()) : MapUtils.EMPTY_MAP;
    }

    public void setOrderResolvers(Map<String, OrderResolver> orderResolvers) {
        this.orderResolvers = orderResolvers;
    }
}
