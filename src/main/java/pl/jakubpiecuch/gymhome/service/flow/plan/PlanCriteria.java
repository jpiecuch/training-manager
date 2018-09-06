package pl.jakubpiecuch.gymhome.service.flow.plan;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.domain.Plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-01-15.
 */
public class PlanCriteria extends Criteria<PlanCriteria> {
    private static final String[] PROPERTIES = new String[]{"id", "goal"};
    private List<Plan.Goal> goals = new ArrayList<Plan.Goal>();

    public PlanCriteria(String lang) {
        super("p", "Plan", lang);
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    public PlanCriteria addGoalRestrictions(Plan.Goal... goal) {
        if (ArrayUtils.isNotEmpty(goal)) {
            this.goals.addAll(Arrays.asList(goal));
        }
        return this;
    }

    @Override
    protected void appendRestrictions() {
        if (this.id == null) {
            collection(this.goals, "goal", "IN");
        }
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
        PlanCriteria rhs = (PlanCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.goals, rhs.goals)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(goals)
                .toHashCode();
    }
}
