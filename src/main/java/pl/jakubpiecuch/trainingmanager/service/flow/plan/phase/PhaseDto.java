package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase;

import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public class PhaseDto extends Flow {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhaseDto phaseDto = (PhaseDto) o;

        if (description != null ? !description.equals(phaseDto.description) : phaseDto.description != null)
            return false;
        if (goal != phaseDto.goal) return false;
        if (id != null ? !id.equals(phaseDto.id) : phaseDto.id != null) return false;
        if (planId != null ? !planId.equals(phaseDto.planId) : phaseDto.planId != null) return false;
        if (position != null ? !position.equals(phaseDto.position) : phaseDto.position != null) return false;
        if (weeks != null ? !weeks.equals(phaseDto.weeks) : phaseDto.weeks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        result = 31 * result + (planId != null ? planId.hashCode() : 0);
        result = 31 * result + (weeks != null ? weeks.hashCode() : 0);
        return result;
    }


    @Override
    public Hierarchy getHierarchy() {
        return Hierarchy.PHASE;
    }
}
