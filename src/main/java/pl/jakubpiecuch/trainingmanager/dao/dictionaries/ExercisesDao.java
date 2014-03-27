package pl.jakubpiecuch.trainingmanager.dao.dictionaries;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises.PartyMusclesEnum;

public interface ExercisesDao {
    List<Exercises> findAll();
    List<Exercises> findByPartyMuscles(PartyMusclesEnum partyMuscles); 
    Exercises findById(Long id);
}
