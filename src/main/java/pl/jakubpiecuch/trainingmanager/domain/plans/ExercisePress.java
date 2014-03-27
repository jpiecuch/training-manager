package pl.jakubpiecuch.trainingmanager.domain.plans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Press;

@Entity
@Table(name = "exercise_press", schema = "plans")
public class ExercisePress extends ExerciseEquipment<Press> implements Serializable {
}
