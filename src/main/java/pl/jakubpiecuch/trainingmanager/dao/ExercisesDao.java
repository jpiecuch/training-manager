package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Exercise.PartyMuscles;

public interface ExercisesDao extends CoreDao {
    List<Exercise> findAll();
    List<Exercise> findByPartyMuscles(PartyMuscles pms);
    Exercise findById(Long id);
    PageResult<Exercise> findPage(int firstResult, int maxResult, PartyMuscles[] partyMuscles);
}
