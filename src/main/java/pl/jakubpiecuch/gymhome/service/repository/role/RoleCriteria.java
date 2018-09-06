package pl.jakubpiecuch.gymhome.service.repository.role;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-03-28.
 */
public class RoleCriteria extends Criteria<RoleCriteria> {

    private static final String[] PROPERTIES = new String[]{CommonEntity.ID_FIELD_NAME, Role.NAME_FIELD_NAME};

    private List<String> names = new ArrayList<String>();

    public RoleCriteria() {
        super("r", "Role", null);
    }

    public RoleCriteria addNameRestrictions(String... name) {
        if (ArrayUtils.isNotEmpty(name)) {
            this.names.addAll(Arrays.asList(name));
        }
        return this;
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    @Override
    protected void appendRestrictions() {
        if (this.id == null) {
            collection(this.names, Role.NAME_FIELD_NAME, "IN");
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
        RoleCriteria rhs = (RoleCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.names, rhs.names)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(names)
                .toHashCode();
    }
}
