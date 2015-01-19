package pl.jakubpiecuch.trainingmanager.service.repository.description;

import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.OrderResolver;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.*;

/**
 * Created by Rico on 2015-01-02.
 */
public class DescriptionCriteria extends Criteria<DescriptionCriteria> {
    private static final String[] PROPERTIES = new String[] {"id","type","muscles","mechanics","force","level"};
    private List<Description.Muscles> muscles = new ArrayList<Description.Muscles>();
    private List<Description.Force> force = new ArrayList<Description.Force>();
    private List<Description.Level> level = new ArrayList<Description.Level>();
    private List<Description.Mechanics> mechanics = new ArrayList<Description.Mechanics>();
    private List<Description.Type> type = new ArrayList<Description.Type>();
    private List<Long> excludedIds = new ArrayList<Long>();

    public DescriptionCriteria(String lang) {
        super("d", "Description", lang);
    }

    @Override
    protected void validateProperty(String property) {
        if (!ArrayUtils.contains(PROPERTIES, property)) {
            throw new IllegalArgumentException();
        }
    }

    public DescriptionCriteria addMuscleRestriction(Description.Muscles... muscle) {
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

    @Override
    protected void appendRestrictions() {
        if (this.id == null) {
            collection(this.muscles, "muscles", "IN");
            collection(this.force, "force", "IN");
            collection(this.level, "level", "IN");
            collection(this.mechanics, "mechanics", "IN");
            collection(this.type, "type", "IN");
            collection(this.excludedIds, "id", "NOT IN");
        }
    }
}
