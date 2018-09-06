package pl.jakubpiecuch.gymhome.service.repository.equipment;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.domain.Equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-01-02.
 */
public class EquipmentCriteria extends Criteria<EquipmentCriteria> {
    private static final String[] PROPERTIES = new String[]{"id", "type"};
    private List<Equipment.Type> types = new ArrayList<Equipment.Type>();

    public EquipmentCriteria(String lang) {
        super("e", "Equipment", lang);
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    public EquipmentCriteria addTypeRestriction(Equipment.Type... types) {
        if (ArrayUtils.isNotEmpty(types)) {
            this.types.addAll(Arrays.asList(types));
        }
        return this;
    }

    @Override
    protected void appendRestrictions() {
        if (this.id == null) {
            collection(Lists.transform(this.types, new Function<Equipment.Type, Integer>() {

                @Override
                public Integer apply(Equipment.Type input) {
                    return input.ordinal();
                }
            }), "discriminatorType", "IN");

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
        EquipmentCriteria rhs = (EquipmentCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.types, rhs.types)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(types)
                .toHashCode();
    }
}
