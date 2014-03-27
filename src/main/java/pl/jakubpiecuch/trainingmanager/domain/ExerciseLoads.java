package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Loads;

@Entity
@Table(name = "exercise_loads")
public class ExerciseLoads extends ExerciseEquipment<Loads> implements Serializable {
}
