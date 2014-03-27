package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Press;

@Entity
@Table(name = "exercise_press")
public class ExercisePress extends ExerciseEquipment<Press> implements Serializable {
}
