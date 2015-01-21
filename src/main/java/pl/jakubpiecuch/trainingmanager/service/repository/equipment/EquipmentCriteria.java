package pl.jakubpiecuch.trainingmanager.service.repository.equipment;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-01-02.
 */
public class EquipmentCriteria extends Criteria<EquipmentCriteria> {
    private static final String[] PROPERTIES = new String[] {"id","type"};
    private List<Equipment.Type> types = new ArrayList<Equipment.Type>();

    public EquipmentCriteria(String lang) {
        super("e", "Equipment", lang);
    }

    @Override
    protected void validateProperty(String property) {
        if (!ArrayUtils.contains(PROPERTIES, property)) {
            throw new IllegalArgumentException();
        }
    }

    public EquipmentCriteria addTypeRestriction(Equipment.Type... types) {
        try {
            this.types.addAll(Arrays.asList(types));
        } catch (NullPointerException ex) {

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
}
