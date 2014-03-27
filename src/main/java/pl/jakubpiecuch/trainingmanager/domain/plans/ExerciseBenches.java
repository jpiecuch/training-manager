package pl.jakubpiecuch.trainingmanager.domain.plans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Benches;

@Entity
@Table(name = "exercise_benches", schema = "plans")
public class ExerciseBenches extends ExerciseEquipment<Benches> implements Serializable {
    
}
