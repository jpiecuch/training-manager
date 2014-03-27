package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Necks;

@Entity
@Table(name = "exercise_necks")
public class ExerciseNecks extends ExerciseEquipment<Necks> implements Serializable {
}
