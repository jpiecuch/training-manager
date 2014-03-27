package pl.jakubpiecuch.trainingmanager.dao.dictionaries.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.dictionaries.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises.PartyMusclesEnum;

public class ExercisesDaoImpl extends HibernateDaoSupport implements ExercisesDao {
    
    @Override
    public List<Exercises> findAll() {
        return session().createQuery("SELECT e FROM Exercises e").list();
    }

    @Override
    public List<Exercises> findByPartyMuscles(PartyMusclesEnum partyMuscles) {
        return session().createQuery("SELECT e FROM Exercises e WHERE e.partyMuscles = :partyMuscles").setParameter("partyMuscles", partyMuscles).list();
    }

    @Override
    public Exercises findById(Long id) {
        return (Exercises) session().get(Exercises.class, id);
    }
    
}
