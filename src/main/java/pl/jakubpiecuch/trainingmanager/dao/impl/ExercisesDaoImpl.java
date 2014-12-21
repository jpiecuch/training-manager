package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.hibernate.Query;
import pl.jakubpiecuch.trainingmanager.dao.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Exercise.PartyMuscles;

import java.util.List;

public class ExercisesDaoImpl extends CoreDaoImpl  implements ExercisesDao {
    
    @Override
    public List<Exercise> findAll() {
        return session().createQuery("SELECT e FROM Exercise e ORDER BY e.id").list();
    }

    @Override
    public List<Exercise> findByPartyMuscles(PartyMuscles partyMuscles) {
        return session().createQuery("SELECT e FROM Exercise e WHERE e.partyMuscles = :partyMuscles").setParameter("partyMuscles", partyMuscles).list();
    }

    @Override
    public Exercise findById(Long id) {
        return (Exercise) session().get(Exercise.class, id);
    }

    @Override
    public PageResult<Exercise> findPage(int firstResult, int maxResult, PartyMuscles[] partyMuscles) {

        StringBuilder sb = new StringBuilder("SELECT e, over(count(*)) FROM Exercise e ");
        if (partyMuscles != null) {
            sb.append("WHERE e.partyMuscles IN (:partyMuscles) ");
        }
        sb.append(" ORDER BY e.id");

        Query query = session().createQuery(sb.toString()).setFirstResult(firstResult).setMaxResults(maxResult);

        if (partyMuscles != null) {
            query.setParameterList("partyMuscles", partyMuscles);
        }

        final List<Object[]> result = query.list();

        return new PageResult<Exercise>() {
            @Override
            public List<Exercise> getResult() {
                return Lists.transform(result, new Function<Object[], Exercise>() {
                    @Override
                    public Exercise apply(Object[] in) {
                        return (Exercise) in[0];
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
