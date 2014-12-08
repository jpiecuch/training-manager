package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

public class DictionaryDBService implements DictionaryService {
    
    private ExercisesDao exercisesDao;
    private EquipmentDao equipmentDao;
    
    @Override
    public PageResult<Exercise> getExercises(int firstResult, int maxResult, Exercise.PartyMuscles[] partyMuscles) {
        return exercisesDao.findPage(firstResult, maxResult, partyMuscles);
    }

    @Override
    public Exercise getExercise(Long id) {
        return exercisesDao.findById(id);
    }

    @Override
    public void save(Exercise exercise) {
        exercisesDao.save(exercise);
    }
    
    @Override
    public List<Equipment> getEquipments(Integer[] type) {
        return equipmentDao.findByType(type);
    }

    @Override
    public Map<Exercise.PartyMuscles, List<Exercise>> getPartyMusclesExercisesList(Exercise.PartyMuscles[] pms) {
        Map<Exercise.PartyMuscles, List<Exercise>> result = new HashMap<Exercise.PartyMuscles, List<Exercise>>();
        for (Exercise.PartyMuscles p : pms) {
            result.put(p, exercisesDao.findByPartyMuscles(p));
        }
        return result;
    }
    
    @Override
    public void save(Equipment equipment) {
        equipmentDao.save(equipment);
    }



    @Autowired
    public void setEquipmentDao(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    @Autowired
    public void setExercisesDao(ExercisesDao exercisesDao) {
        this.exercisesDao = exercisesDao;
    }
}
