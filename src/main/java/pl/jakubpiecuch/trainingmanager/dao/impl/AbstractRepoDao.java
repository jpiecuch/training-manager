package pl.jakubpiecuch.trainingmanager.dao.impl;


import pl.jakubpiecuch.trainingmanager.dao.EmptyPageResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.RepoCommonEntity;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;

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

    public void setOrderResolvers(Map<String, OrderResolver> orderResolvers) {
        this.orderResolvers = orderResolvers;
    }
}
