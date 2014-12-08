package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;
import java.util.Map;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Exercise.PartyMuscles;

public interface DictionaryService {

    PageResult<Exercise> getExercises(int firstResult, int maxResults, PartyMuscles[] partyMuscles);
    Exercise getExercise(Long id);
    void save(Exercise exercise);
    Map<Exercise.PartyMuscles, List<Exercise>> getPartyMusclesExercisesList(Exercise.PartyMuscles[] pms);
    List<Equipment> getEquipments(Integer[] type);
    void save(Equipment equipment);
    
}
