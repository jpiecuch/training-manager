package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phase")
public class Phase extends CommonEntity implements Serializable {
    public static final String GOAL_FIELD = "goal";
    public static final String POSITION_FIELD = "position";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String PLAN_FIELD = "plan";
    public static final String WEEKS_FIELD = "weeks";
    private static final long serialVersionUID = 1L;
    private Integer position;
    private Plan.Goal goal;
    private String description;
    private Plan plan;
    private Integer weeks;
    private List<Workout> workouts = new ArrayList<Workout>();

    public Phase() {
        super();
    }

    public Phase(Long id) {
        super(id);
    }

    @Column(name = GOAL_FIELD)
    @Enumerated(EnumType.ORDINAL)
    public Plan.Goal getGoal() {
        return goal;
    }

    public void setGoal(Plan.Goal goal) {
        this.goal = goal;
    }

    @Column(name = POSITION_FIELD)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = DESCRIPTION_FIELD)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PLAN_FIELD)
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Column(name = WEEKS_FIELD)
    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = Workout.PHASE_FIELD, orphanRemoval = true)
    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Phase rhs = (Phase) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.position, rhs.position)
                .append(this.goal, rhs.goal)
                .append(this.description, rhs.description)
                .append(this.plan, rhs.plan)
                .append(this.weeks, rhs.weeks)
                .append(this.workouts, rhs.workouts)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(position)
                .append(goal)
                .append(description)
                .append(plan)
                .append(weeks)
                .append(workouts)
                .toHashCode();
    }
}
