package pl.jakubpiecuch.trainingmanager.service.repository.description;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Restrictions;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void fillDaoCriteria(org.hibernate.Criteria criteria) {
        if (this.id != null) {
            criteria.add(Restrictions.eq("id", this.id));
        }

        if (this.firstResult != null) {
            criteria.setFirstResult(firstResult);
        }

        if (this.maxResults != null) {
            criteria.setMaxResults(maxResults);
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
