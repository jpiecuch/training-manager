package pl.jakubpiecuch.trainingmanager.service.repository.description;

import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if(ArrayUtils.isNotEmpty(muscle)) {
            this.muscles.addAll(Arrays.asList(muscle));
        }
        return this;
    }

    public DescriptionCriteria addForceRestriction(Description.Force... force) {
        if(ArrayUtils.isNotEmpty(force)) {
            this.force.addAll(Arrays.asList(force));
        }
        return this;
    }

    public DescriptionCriteria addLevelRestriction(Description.Level... level) {
        if(ArrayUtils.isNotEmpty(level)) {
            this.level.addAll(Arrays.asList(level));
        }
        return this;
    }

    public DescriptionCriteria addMechanicsRestriction(Description.Mechanics... mechanics) {
        if(ArrayUtils.isNotEmpty(mechanics)) {
            this.mechanics.addAll(Arrays.asList(mechanics));
        }
        return this;
    }

    public DescriptionCriteria addTypeRestriction(Description.Type... type) {
        if(ArrayUtils.isNotEmpty(type)) {
            this.type.addAll(Arrays.asList(type));
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
