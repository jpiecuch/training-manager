package pl.jakubpiecuch.trainingmanager.domain.plans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Necks;

@Entity
@Table(name = "exercise_necks", schema = "plans")
public class ExerciseNecks extends ExerciseEquipment<Necks> implements Serializable {
}
