package pl.jakubpiecuch.trainingmanager.service.repository.equipment;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Restrictions;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-01-02.
 */
public class EquipmentCriteria extends Criteria<EquipmentCriteria> {
    private List<Equipment.Type> types = new ArrayList<Equipment.Type>();
    private List<Long> excludedIds = new ArrayList<Long>();

    public EquipmentCriteria addTypeRestriction(Equipment.Type... types) {
        try {
            this.types.addAll(Arrays.asList(types));
        } catch (NullPointerException ex) {

        }
        return this;
    }

    public EquipmentCriteria addExcludedIdRestriction(Long... ids) {
        try {
            this.excludedIds.addAll(Arrays.asList(ids));
        } catch (NullPointerException ex) {

        }
        return this;
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

        if (CollectionUtils.isNotEmpty(types)) {
            List<Integer> intTypes = new ArrayList<Integer>();
            for(Equipment.Type type : types) {
                intTypes.add(type.ordinal());
            }
            criteria.add(Restrictions.in("discriminatorType", intTypes));
        }

        if (CollectionUtils.isNotEmpty(excludedIds)) {
            criteria.add(Restrictions.not(Restrictions.in("id", excludedIds)));
        }

    }
}
