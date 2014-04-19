package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.Exercises.PartyMuscles;

public interface DictionaryService {

    List<Exercises> getExercises();
    Exercises getExercise(Long id);
    void saveExercise(Exercises exercise);
    List<Exercises> getPartyMusclesExercisesList(PartyMuscles pms);
    EquipmentSet getEquipmentSet();
    List<Equipment> getEquipments(Equipment.Type type);
    void saveEquipment(Equipment equipment);
    
}
