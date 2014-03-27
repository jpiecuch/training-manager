package pl.jakubpiecuch.trainingmanager.domain.plans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Dumbbells;

@Entity
@Table(name = "exercise_dumbbells", schema = "plans")
public class ExerciseDumbbells extends ExerciseEquipment<Dumbbells> implements Serializable {    
}
