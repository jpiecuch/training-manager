package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workout")
public class Workout extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum WeekDay { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY }

    private WeekDay weekDay;
    private Phase phase;
    private Integer position;
    private Description.PartyMuscles muscles;

    public Workout() {
    }

    public Workout(Long id) {
        super(id);
    }

    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "week_day")
    @Enumerated(EnumType.ORDINAL)
    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase")
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @Column(name = "muscles")
    @Enumerated(EnumType.ORDINAL)
    public Description.PartyMuscles getMuscles() {
        return muscles;
    }

    public void setMuscles(Description.PartyMuscles muscles) {
        this.muscles = muscles;
    }
}
