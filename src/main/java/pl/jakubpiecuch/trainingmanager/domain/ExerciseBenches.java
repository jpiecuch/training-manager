package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Benches;

@Entity
@Table(name = "exercise_benches")
public class ExerciseBenches extends ExerciseEquipment<Benches> implements Serializable {
    
}
