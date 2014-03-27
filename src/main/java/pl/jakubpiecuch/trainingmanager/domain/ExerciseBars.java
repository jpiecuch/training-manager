/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Bars;

@Entity
@Table(name = "exercise_bars")
public class ExerciseBars extends ExerciseEquipment<Bars> implements Serializable {
    
}
