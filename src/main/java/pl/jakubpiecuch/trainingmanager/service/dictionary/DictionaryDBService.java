package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.dao.dictionaries.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.dictionaries.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises.PartyMusclesEnum;

@Service
public class DictionaryDBService implements DictionaryService {
    
    private ExercisesDao exercisesDao;
    private CoreDao coreDao;
    private Map<Equipment.Type, EquipmentDao> daos;
    
    @Override
    public List<Exercises> getExercises() {
        return exercisesDao.findAll();
    }

    @Override
    public Exercises getExercise(Long id) {
        return exercisesDao.findById(id);
    }

    @Override
    public void saveExercise(Exercises exercise) {
        coreDao.save(exercise);
    }
    
    @Override
    public List<Equipment> getEquipments(Equipment.Type type) {
        return daos.get(type).findAll();
    }
    
    @Override
    public void saveEquipment(Equipment equipment) {
        coreDao.save(equipment);
    }

    @Override
    public List<Exercises> getPartyMusclesExercisesList(PartyMusclesEnum pms) {
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
    public void setCoreDao(CoreDao coreDao) {
        this.coreDao = coreDao;
    }

    @Autowired
    public void setExercisesDao(ExercisesDao exercisesDao) {
        this.exercisesDao = exercisesDao;
    }
}
