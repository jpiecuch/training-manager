package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.Exercises.PartyMusclesEnum;

public interface ExercisesDao extends CoreDao {
    List<Exercises> findAll();
    List<Exercises> findByPartyMuscles(PartyMusclesEnum partyMuscles); 
    Exercises findById(Long id);
}
