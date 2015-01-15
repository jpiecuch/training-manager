package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.*;

/**
 * Created by Rico on 2015-01-15.
 */
public class PlanCriteria extends Criteria<PlanCriteria> {
    private List<Plan.Goal> goals = new ArrayList<Plan.Goal>();


    public PlanCriteria addGoalRestrictions(Plan.Goal... goal) {
        try {
            this.goals.addAll(Arrays.asList(goal));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public Query createQuery(Session session) {
        StringBuilder sb = new StringBuilder("SELECT p, over(count(*)) FROM Plan p ");
        List<String> restrictions = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();

        if (CollectionUtils.isNotEmpty(this.goals)) {
            restrictions.add(" p.goal IN (:goal) ");
            params.put("goal", this.goals);
        }

        if (!restrictions.isEmpty()) {
            sb.append(" WHERE ");
        }

        for (int i = 0; i < restrictions.size(); i++) {
            String restriction = restrictions.get(i);
            sb.append((i > 0 ? " AND " : "") + restriction);
        }

        Query query = session.createQuery(sb.toString());
        if (this.firstResult != null) {
            query.setFirstResult(this.firstResult);
        }
        if (this.maxResults != null) {
            query.setMaxResults(this.maxResults);
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof List) {
                query.setParameterList(entry.getKey(), (Collection) entry.getValue());
            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }


        return query;
    }
}
