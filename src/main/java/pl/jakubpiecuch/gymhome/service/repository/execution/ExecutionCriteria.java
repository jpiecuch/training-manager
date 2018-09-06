package pl.jakubpiecuch.gymhome.service.repository.execution;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;

/**
 * Created by Rico on 2015-01-02.
 */
public class ExecutionCriteria extends Criteria<ExecutionCriteria> {
    private static final String[] PROPERTIES = new String[]{};

    public ExecutionCriteria(String lang) {
        super("e", "Execution", lang);
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    @Override
    protected void appendRestrictions() {
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
        ExecutionCriteria rhs = (ExecutionCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .toHashCode();
    }
}
