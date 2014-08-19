package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.Exercises.PartyMuscles;

public interface DictionaryService {

    PageResult<Exercises> getExercises(int firstResult, int maxResults);
    Exercises getExercise(Long id);
    void save(Exercises exercise);
    List<Exercises> getPartyMusclesExercisesList(PartyMuscles pms);
    EquipmentSet getEquipmentSet();
    List<Equipment> getEquipments(Equipment.Type type);
    void save(Equipment equipment);
    
}
