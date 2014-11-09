package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Exercise.PartyMuscles;

public interface DictionaryService {

    PageResult<Exercise> getExercises(int firstResult, int maxResults, PartyMuscles[] partyMuscles);
    Exercise getExercise(Long id);
    void save(Exercise exercise);
    List<Exercise> getPartyMusclesExercisesList(PartyMuscles pms);
    EquipmentSet getEquipmentSet();
    List<Equipment> getEquipments(Equipment.Type type);
    void save(Equipment equipment);
    
}
