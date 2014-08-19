package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;

public class DictionaryDBService implements DictionaryService {
    
    private ExercisesDao exercisesDao;
    private Map<Equipment.Type, EquipmentDao> daos;
    
    @Override
    public PageResult<Exercises> getExercises(int firstResult, int maxResult) {
        return exercisesDao.findPage(firstResult, maxResult);
    }

    @Override
    public Exercises getExercise(Long id) {
        return exercisesDao.findById(id);
    }

    @Override
    public void save(Exercises exercise) {
        exercisesDao.save(exercise);
    }
    
    @Override
    public List<Equipment> getEquipments(Equipment.Type type) {
        return daos.get(type).findAll();
    }
    
    @Override
    public void save(Equipment equipment) {
        daos.get(equipment.getEquipmentType()).save(equipment);
    }

    @Override
    public List<Exercises> getPartyMusclesExercisesList(Exercises.PartyMuscles pms) {
        return exercisesDao.findByPartyMuscles(pms);
    }
    
    @Override
    public EquipmentSet getEquipmentSet() {
        EquipmentSet result = new EquipmentSet();
        result.setBenches(daos.get(Equipment.Type.benches).findAll());
        result.setDumbbells(daos.get(Equipment.Type.dumbbells).findAll());
        result.setLoads(daos.get(Equipment.Type.loads).findAll());
        result.setNecks(daos.get(Equipment.Type.necks).findAll());
        result.setStands(daos.get(Equipment.Type.stands).findAll());
        result.setBars(daos.get(Equipment.Type.bars).findAll());
        result.setPress(daos.get(Equipment.Type.press).findAll());
        return result;
    }

    public void setDaos(Map<Equipment.Type, EquipmentDao> daos) {
        this.daos = daos;
    }

    @Autowired
    public void setExercisesDao(ExercisesDao exercisesDao) {
        this.exercisesDao = exercisesDao;
    }
}
