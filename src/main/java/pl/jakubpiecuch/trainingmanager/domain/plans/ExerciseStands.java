package pl.jakubpiecuch.trainingmanager.domain.plans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Stands;

@Entity
@Table(name = "exercise_stands", schema = "plans")
public class ExerciseStands extends ExerciseEquipment<Stands> implements Serializable {
}
