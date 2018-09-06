package pl.jakubpiecuch.gymhome.service.flow.plan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.gymhome.service.repository.RepoObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PlanDto implements RepoObject {

    private Long id;
    private String name;
    private Plan.Goal goal;
    private Long creatorId;
    private List<PhaseDto> phases = new ArrayList<>();
    private Boolean editable;
    private boolean used;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public List<PhaseDto> getPhases() {
        return phases;
    }

    public void setPhases(List<PhaseDto> phases) {
        this.phases = phases;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
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
        PlanDto rhs = (PlanDto) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.name, rhs.name)
                .append(this.goal, rhs.goal)
                .append(this.creatorId, rhs.creatorId)
                .append(this.phases, rhs.phases)
                .append(this.editable, rhs.editable)
                .append(this.used, rhs.used)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(goal)
                .append(creatorId)
                .append(phases)
                .append(editable)
                .append(used)
                .toHashCode();
    }
}
