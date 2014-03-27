package pl.jakubpiecuch.trainingmanager.domain.plans;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import pl.jakubpiecuch.trainingmanager.domain.Entity;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Equipment;

@MappedSuperclass()
public class ExerciseEquipment<T extends Equipment> extends Entity {

    public ExerciseEquipment() {
    }
    
    public ExerciseEquipment(Long id) {
        super(id);
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise")
    private DayExercises exercise;
    
    public DayExercises getExercise() {
        return exercise;
    }

    public void setExercise(DayExercises exercise) {
        this.exercise = exercise;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment")
    private T equipment;

    public T getEquipment() {
        return equipment;
    }

    public void setEquipment(T equipment) {
        this.equipment = equipment;
    }
}
