package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanDto extends Flow {

    private Long id;
    private String name;
    private Plan.Goal goal;
    private Long creatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plan.Goal getGoal() {
        return goal;
    }

    public void setGoal(Plan.Goal goal) {
        this.goal = goal;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public Hierarchy getHierarchy() {
        return Hierarchy.PLAN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanDto planDto = (PlanDto) o;

        if (goal != planDto.goal) return false;
        if (id != null ? !id.equals(planDto.id) : planDto.id != null) return false;
        if (name != null ? !name.equals(planDto.name) : planDto.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        return result;
    }
}