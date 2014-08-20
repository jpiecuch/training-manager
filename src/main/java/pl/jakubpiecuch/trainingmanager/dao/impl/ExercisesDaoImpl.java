package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import pl.jakubpiecuch.trainingmanager.dao.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.Exercises.PartyMuscles;

public class ExercisesDaoImpl extends CoreDaoImpl  implements ExercisesDao {
    
    @Override
    public List<Exercises> findAll() {
        return session().createQuery("SELECT e FROM Exercises e ORDER BY e.id").list();
    }

    @Override
    public List<Exercises> findByPartyMuscles(PartyMuscles partyMuscles) {
        return session().createQuery("SELECT e FROM Exercises e WHERE e.partyMuscles = :partyMuscles").setParameter("partyMuscles", partyMuscles).list();
    }

    @Override
    public Exercises findById(Long id) {
        return (Exercises) session().get(Exercises.class, id);
    }

    @Override
    public PageResult<Exercises> findPage(int firstResult, int maxResult, PartyMuscles[] partyMuscles) {

        StringBuilder sb = new StringBuilder("SELECT e, over(count(*)) FROM Exercises e ");
        if (partyMuscles != null) {
            sb.append("WHERE e.partyMuscles IN (:partyMuscles) ");
        }
        sb.append(" ORDER BY e.id");

        Query query = session().createQuery(sb.toString()).setFirstResult(firstResult).setMaxResults(maxResult);

        if (partyMuscles != null) {
            query.setParameterList("partyMuscles", partyMuscles);
        }

        final List<Object[]> result = query.list();

        return new PageResult<Exercises>() {
            @Override
            public List<Exercises> getResult() {
                return Lists.transform(result, new Function<Object[], Exercises>() {
                    @Override
                    public Exercises apply(Object[] in) {
                        return (Exercises) in[0];
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
