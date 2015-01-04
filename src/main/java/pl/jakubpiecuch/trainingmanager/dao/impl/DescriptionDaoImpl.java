package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.hibernate.Criteria;
import org.hibernate.Query;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Description.PartyMuscles;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import java.util.List;

public class DescriptionDaoImpl extends CoreDaoImpl  implements DescriptionDao {

    @Override
    public Long count() {
        return (Long) session().createQuery("SELECT count(*) FROM Description").uniqueResult();
    }

    @Override
    public PageResult<Description> findByCriteria(DescriptionCriteria criteria) {
        Criteria daoCriteria = session().createCriteria(Description.class);
        criteria.fillDaoCriteria(daoCriteria);
        final List list = daoCriteria.list();
        final long count = count();
        return new PageResult<Description>() {
            @Override
            public List<Description> getResult() {
                return list;
            }

            @Override
            public long getCount() {
                return count;
            }
        };
    }

    @Override
    public List<Description> findAll() {
        return session().createQuery("SELECT e FROM Description e ORDER BY e.id").list();
    }

    @Override
    public List<Description> findByPartyMuscles(PartyMuscles partyMuscles) {
        return session().createQuery("SELECT e FROM Description e WHERE e.partyMuscles = :partyMuscles").setParameter("partyMuscles", partyMuscles).list();
    }

    @Override
    public Description findById(Long id) {
        return (Description) session().get(Description.class, id);
    }

    @Override
    public PageResult<Description> findPage(int firstResult, int maxResult, PartyMuscles[] partyMuscles) {

        StringBuilder sb = new StringBuilder("SELECT e, over(count(*)) FROM Description e ");
        if (partyMuscles != null) {
            sb.append("WHERE e.partyMuscles IN (:partyMuscles) ");
        }
        sb.append(" ORDER BY e.id");

        Query query = session().createQuery(sb.toString()).setFirstResult(firstResult).setMaxResults(maxResult);

        if (partyMuscles != null) {
            query.setParameterList("partyMuscles", partyMuscles);
        }

        final List<Object[]> result = query.list();

        return new PageResult<Description>() {
            @Override
            public List<Description> getResult() {
                return Lists.transform(result, new Function<Object[], Description>() {
                    @Override
                    public Description apply(Object[] in) {
                        return (Description) in[0];
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
