package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Dumbbells;

@Entity
@Table(name = "exercise_dumbbells")
public class ExerciseDumbbells extends ExerciseEquipment<Dumbbells> implements Serializable {    
}
