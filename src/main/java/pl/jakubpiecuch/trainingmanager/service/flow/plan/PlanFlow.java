package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowObject;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanFlow extends FlowObject {

    private Long id;
    private String name;
    private Plan.Goal goal;

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

    @Override
    public int getFlowPosition() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanFlow planFlow = (PlanFlow) o;

        if (goal != planFlow.goal) return false;
        if (id != null ? !id.equals(planFlow.id) : planFlow.id != null) return false;
        if (name != null ? !name.equals(planFlow.name) : planFlow.name != null) return false;

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
