package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan extends RepoCommonEntity {
    public static final String NAME_FIELD = "name";
    public static final String GOAL_FIELD = "goal";
    public static final String CREATOR_FIELD = "creator";
    public static final String USED_FIELD = "used";
    private static final long serialVersionUID = 1L;
    private String name;
    private Goal goal;
    private Account creator;
    private boolean used;
    private List<Phase> phases = new ArrayList<Phase>();
    public Plan() {
        super();
    }

    public Plan(Long id) {
        super(id);
    }

    @Column(name = NAME_FIELD)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = GOAL_FIELD)
    @Enumerated(EnumType.ORDINAL)
    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CREATOR_FIELD)
    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    @Column(name = USED_FIELD)
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @OneToMany(mappedBy = Phase.PLAN_FIELD, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
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
        Plan rhs = (Plan) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.name, rhs.name)
                .append(this.goal, rhs.goal)
                .append(this.creator, rhs.creator)
                .append(this.used, rhs.used)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(goal)
                .append(creator)
                .append(used)
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Plan{");
        sb.append("used=").append(used);
        sb.append(", name='").append(name).append('\'');
        sb.append(", goal=").append(goal);
        sb.append(", creator=").append(creator);
        sb.append('}');
        return sb.toString();
    }

    public enum Goal {
        MUSCLES, STRENGTH
    }
}
