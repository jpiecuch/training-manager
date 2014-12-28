package pl.jakubpiecuch.trainingmanager.service.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryDBService implements DictionaryService {
    
    private ExerciseDao exerciseDao;
    private EquipmentDao equipmentDao;
    
    @Override
    public PageResult<Exercise> getExercises(int firstResult, int maxResult, Exercise.PartyMuscles[] partyMuscles) {
        return exerciseDao.findPage(firstResult, maxResult, partyMuscles);
    }

    @Override
    public Exercise getExercise(Long id) {
        return exerciseDao.findById(id);
    }

    @Override
    public void save(Exercise exercise) {
        exerciseDao.save(exercise);
    }
    
    @Override
    public List<Equipment> getEquipments(Integer[] type) {
        return equipmentDao.findByType(type);
    }

    @Override
    public Map<Exercise.PartyMuscles, List<Exercise>> getPartyMusclesExercisesList(Exercise.PartyMuscles[] pms) {
        Map<Exercise.PartyMuscles, List<Exercise>> result = new HashMap<Exercise.PartyMuscles, List<Exercise>>();
        for (Exercise.PartyMuscles p : pms) {
            result.put(p, exerciseDao.findByPartyMuscles(p));
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
    public void setExerciseDao(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }
}
