package pl.jakubpiecuch.trainingmanager.domain.plans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Loads;

@Entity
@Table(name = "exercise_loads", schema = "plans")
public class ExerciseLoads extends ExerciseEquipment<Loads> implements Serializable {
}
