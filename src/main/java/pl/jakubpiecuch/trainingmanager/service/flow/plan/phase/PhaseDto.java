package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.identify.IdentifyObject;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public class PhaseDto implements IdentifyObject {

    private Long id;
    private Integer position;
    private String description;
    private Plan.Goal goal;
    private Long planId;
    private Integer weeks;
    private List<WorkoutDto> workouts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plan.Goal getGoal() {
        return goal;
    }

    public void setGoal(Plan.Goal goal) {
        this.goal = goal;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public List<WorkoutDto> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<WorkoutDto> workouts) {
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
        PhaseDto rhs = (PhaseDto) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.id, rhs.id)
                .append(this.position, rhs.position)
                .append(this.description, rhs.description)
                .append(this.goal, rhs.goal)
                .append(this.planId, rhs.planId)
                .append(this.weeks, rhs.weeks)
                .append(this.workouts, rhs.workouts)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(id)
                .append(position)
                .append(description)
                .append(goal)
                .append(planId)
                .append(weeks)
                .append(workouts)
                .toHashCode();
    }
}
