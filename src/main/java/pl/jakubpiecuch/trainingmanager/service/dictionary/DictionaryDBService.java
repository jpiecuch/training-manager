package pl.jakubpiecuch.trainingmanager.service.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Description;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryDBService implements DictionaryService {
    
    private DescriptionDao descriptionDao;
    private EquipmentDao equipmentDao;
    
    @Override
    public PageResult<Description> getExercises(int firstResult, int maxResult, Description.PartyMuscles[] partyMuscles) {
        return descriptionDao.findPage(firstResult, maxResult, partyMuscles);
    }

    @Override
    public Description getExercise(Long id) {
        return descriptionDao.findById(id);
    }

    @Override
    public void save(Description description) {
        descriptionDao.save(description);
    }
    
    @Override
    public List<Equipment> getEquipments(Integer[] type) {
        return equipmentDao.findByType(type);
    }

    @Override
    public Map<Description.PartyMuscles, List<Description>> getPartyMusclesExercisesList(Description.PartyMuscles[] pms) {
        Map<Description.PartyMuscles, List<Description>> result = new HashMap<Description.PartyMuscles, List<Description>>();
        for (Description.PartyMuscles p : pms) {
            result.put(p, descriptionDao.findByPartyMuscles(p));
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
    public void setDescriptionDao(DescriptionDao descriptionDao) {
        this.descriptionDao = descriptionDao;
    }
}
