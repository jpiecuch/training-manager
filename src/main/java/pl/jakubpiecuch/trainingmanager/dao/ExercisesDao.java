package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.Exercises.PartyMuscles;

public interface ExercisesDao extends CoreDao {
    List<Exercises> findAll();
    List<Exercises> findByPartyMuscles(PartyMuscles pms); 
    Exercises findById(Long id);
    PageResult<Exercises> findPage(int firstResult, int maxResult);
}
