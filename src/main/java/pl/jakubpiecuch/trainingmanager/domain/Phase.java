package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "phase")
public class Phase extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer position;
    private Plan.Goal goal;
    private String description;
    private Plan plan;
    private Integer weeks;
    private List<Workout> workouts;

    public Phase() {
    }

    public Phase(Long id) {
        super(id);
    }

    @Column(name = "goal")
    @Enumerated(EnumType.ORDINAL)
    public Plan.Goal getGoal() {
        return goal;
    }

    public void setGoal(Plan.Goal goal) {
        this.goal = goal;
    }

    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan")
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Column(name = "weeks")
    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phase")
    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
