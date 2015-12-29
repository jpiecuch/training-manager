package pl.jakubpiecuch.trainingmanager.dao.impl;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.EmptyPageResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2015-02-21.
 */
public abstract class AbstractRepoDao<E extends CommonEntity, C extends Criteria> extends CoreDaoImpl<E> implements RepoDao<E, C> {

    @Override
    public PageResult<E> findByCriteria(C criteria) {
        return criteria != null ? criteria.page(session()): new EmptyPageResult<>();
    }
}
