package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-01-15.
 */
public class PlanCriteria extends Criteria<PlanCriteria> {
    private static final String[] PROPERTIES = new String[] {"id","goal"};
    private List<Plan.Goal> goals = new ArrayList<Plan.Goal>();

    public PlanCriteria(String lang) {
        super("p", "Plan", lang);
    }

    @Override
    protected void validateProperty(String property) {
        if (!ArrayUtils.contains(PROPERTIES, property)) {
            throw new IllegalArgumentException();
        }
    }

    public PlanCriteria addGoalRestrictions(Plan.Goal... goal) {
        try {
            this.goals.addAll(Arrays.asList(goal));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    @Override
    protected void appendRestrictions() {
        if (this.id == null) {
            collection(this.goals, "goal", "IN");
        }
    }
}
