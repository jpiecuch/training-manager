package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
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
    public PageResult<Exercises> findPage(int firstResult, int maxResult) {

        final List<Object[]> result = session().createQuery("SELECT e, over(count(*)) FROM Exercises e ORDER BY e.id").setFirstResult(firstResult).setMaxResults(maxResult).list();

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
