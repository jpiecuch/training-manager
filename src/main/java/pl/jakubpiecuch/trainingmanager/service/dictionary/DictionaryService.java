package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises.PartyMusclesEnum;

public interface DictionaryService {

    List<Exercises> getExercises();
    Exercises getExercise(Long id);
    void saveExercise(Exercises exercise);
    List<Exercises> getPartyMusclesExercisesList(PartyMusclesEnum pms);
    EquipmentSet getEquipmentSet();

    List<Equipment> getEquipments(Equipment.Type type);

    void saveEquipment(Equipment equipment);
    
}
