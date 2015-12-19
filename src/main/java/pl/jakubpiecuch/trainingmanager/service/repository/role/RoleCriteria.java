package pl.jakubpiecuch.trainingmanager.service.repository.role;

import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

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
}
