package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.execution.session.SessionExecutionCriteria;

import java.util.List;

public class ExecutionDaoImpl extends CoreDaoImpl implements ExecutionDao, RepoDao<Execution, SessionExecutionCriteria> {


    @Override
    public Execution findById(long id) {
        return (Execution) session().createQuery("SELECT e FROM Execution e WHERE e.id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<Execution> findByParentId(long parentId) {
        return session().createQuery("SELECT e FROM Execution e WHERE e.exercise.id = :exerciseId").setParameter("exerciseId", parentId).list();
    }

    @Override
    public PageResult<Execution> findByCriteria(SessionExecutionCriteria criteria) {
        final List<Object[]> result = criteria.query(session()).list();

        return new PageResult<Execution>() {
            @Override
            public List<Execution> getResult() {
                return Lists.transform(result, new Function<Object[], Execution>() {
                    @Override
                    public Execution apply(Object[] in) {
                        return (Execution) in[0];
                    }
                });
            }

            @Override
            public long getCount() {
                return result.size() > 0 ? (Long)result.get(0)[1] : 0;
            }
        };
    }
}