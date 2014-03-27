package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Stands;

@Entity
@Table(name = "exercise_stands")
public class ExerciseStands extends ExerciseEquipment<Stands> implements Serializable {
}
