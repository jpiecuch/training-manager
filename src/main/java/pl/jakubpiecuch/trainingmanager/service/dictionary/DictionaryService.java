package pl.jakubpiecuch.trainingmanager.service.dictionary;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Description.PartyMuscles;

import java.util.List;
import java.util.Map;

public interface DictionaryService {

    PageResult<Description> getExercises(int firstResult, int maxResults, PartyMuscles[] partyMuscles);
    Description getExercise(Long id);
    void save(Description description);
    Map<Description.PartyMuscles, List<Description>> getPartyMusclesExercisesList(Description.PartyMuscles[] pms);
    List<Equipment> getEquipments(Integer[] type);
    void save(Equipment equipment);
    
}
