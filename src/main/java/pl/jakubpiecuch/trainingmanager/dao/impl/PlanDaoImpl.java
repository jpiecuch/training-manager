package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;

import java.util.List;

public class PlanDaoImpl extends CoreDaoImpl implements PlanDao, RepoDao<Plan, PlanCriteria> {

    @Override
    public PageResult<Plan> findByCriteria(PlanCriteria criteria) {
        final List<Object[]> result = criteria.query(session()).list();

        return new PageResult<Plan>() {
            @Override
            public List<Plan> getResult() {
                return Lists.transform(result, new Function<Object[], Plan>() {
                    @Override
                    public Plan apply(Object[] in) {
                        return (Plan) in[0];
                    }
                });
            }

            @Override
            public long getCount() {
                return result.size() > 0 ? (Long)result.get(0)[1] : 0;
            }
        };
    }

    @Override
    public Plan findById(long id) {
        return (Plan) session().createQuery("SELECT p FROM Plan p WHERE p.id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<Plan> findByParentId(long parentId) {
        throw new IllegalArgumentException();
    }
}