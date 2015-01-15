package pl.jakubpiecuch.trainingmanager.service.repository.description;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.*;

/**
 * Created by Rico on 2015-01-02.
 */
public class DescriptionCriteria extends Criteria<DescriptionCriteria> {
    private List<Description.PartyMuscles> muscles = new ArrayList<Description.PartyMuscles>();
    private List<Description.Force> force = new ArrayList<Description.Force>();
    private List<Description.Level> level = new ArrayList<Description.Level>();
    private List<Description.Mechanics> mechanics = new ArrayList<Description.Mechanics>();
    private List<Description.Type> type = new ArrayList<Description.Type>();
    private List<Long> excludedIds = new ArrayList<Long>();

    public DescriptionCriteria addMuscleRestriction(Description.PartyMuscles... muscle) {
        try {
            this.muscles.addAll(Arrays.asList(muscle));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public DescriptionCriteria addForceRestriction(Description.Force... force) {
        try {
            this.force.addAll(Arrays.asList(force));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public DescriptionCriteria addLevelRestriction(Description.Level... level) {
        try {
            this.level.addAll(Arrays.asList(level));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public DescriptionCriteria addMechanicsRestriction(Description.Mechanics... mechanics) {
        try {
            this.mechanics.addAll(Arrays.asList(mechanics));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public DescriptionCriteria addTypeRestriction(Description.Type... type) {
        try {
            this.type.addAll(Arrays.asList(type));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public DescriptionCriteria addExcludedIdRestriction(Long... ids) {
        try {
            this.excludedIds.addAll(Arrays.asList(ids));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public Query createQuery(Session session) {
        StringBuilder sb = new StringBuilder("SELECT d, over(count(*)) FROM Description d ");
        List<String> restrictions = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();

        if (CollectionUtils.isNotEmpty(this.muscles)) {
            restrictions.add(" d.partyMuscles IN (:partyMuscles) ");
            params.put("partyMuscles", this.muscles);
        }

        if (CollectionUtils.isNotEmpty(this.force)) {
            restrictions.add(" d.force IN (:force) ");
            params.put("force", this.force);
        }

        if (CollectionUtils.isNotEmpty(this.level)) {
            restrictions.add(" d.level IN (:level) ");
            params.put("level", this.level);
        }

        if (CollectionUtils.isNotEmpty(this.mechanics)) {
            restrictions.add(" d.mechanics IN (:mechanics) ");
            params.put("mechanics", this.mechanics);
        }

        if (CollectionUtils.isNotEmpty(this.type)) {
            restrictions.add(" d.type IN (:type) ");
            params.put("type", this.type);
        }

        if (CollectionUtils.isNotEmpty(this.excludedIds)) {
            restrictions.add(" d.id NOT IN (:excludeIds) ");
            params.put("excludeIds", this.excludedIds);
        }

        if(!restrictions.isEmpty()) {
            sb.append(" WHERE ");
        }

        for (int i =0; i < restrictions.size(); i++) {
            String restriction = restrictions.get(i);
            sb.append((i > 0 ? " AND " : "") + restriction);
        }

        Query query = session.createQuery(sb.toString());
        if ( this.firstResult != null) {
            query.setFirstResult(this.firstResult);
        }
        if (this.maxResults != null) {
            query.setMaxResults(this.maxResults);
        }

        for(Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof List) {
                query.setParameterList(entry.getKey(), (Collection) entry.getValue());
            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }


        return query;
    }

    public void fillDaoCriteria(org.hibernate.Criteria criteria) {
        if (this.id != null) {
            criteria.add(Restrictions.eq("id", this.id));
            criteria.setFirstResult(0);
            criteria.setMaxResults(1);
        } else {
            if (this.firstResult != null) {
                criteria.setFirstResult(firstResult);
            }

            if (this.maxResults != null) {
                criteria.setMaxResults(maxResults);
            }
        }

        if (CollectionUtils.isNotEmpty(muscles)) {
            criteria.add(Restrictions.in("partyMuscles", muscles));
        }

        if (CollectionUtils.isNotEmpty(force)) {
            criteria.add(Restrictions.in("force", force));
        }

        if (CollectionUtils.isNotEmpty(level)) {
            criteria.add(Restrictions.in("level", level));
        }

        if (CollectionUtils.isNotEmpty(mechanics)) {
            criteria.add(Restrictions.in("mechanics", mechanics));
        }

        if (CollectionUtils.isNotEmpty(type)) {
            criteria.add(Restrictions.in("type", type));
        }

        if (CollectionUtils.isNotEmpty(excludedIds)) {
            criteria.add(Restrictions.not(Restrictions.in("id", excludedIds)));
        }

    }
}
