package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.hibernate.Criteria;
import org.hibernate.Query;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.equipment.EquipmentCriteria;

import java.util.List;

public class EquipmentDaoImpl extends CoreDaoImpl implements EquipmentDao {

    @Override
    public Long count() {
        return (Long) session().createQuery("SELECT count(*) FROM Equipment").uniqueResult();
    }

    @Override
    public PageResult<Equipment> findByCriteria(EquipmentCriteria criteria) {

        Query query = criteria.query(session());
        final List<Object[]> result = query.list();

        return new PageResult<Equipment>() {
            @Override
            public List<Equipment> getResult() {
                return Lists.transform(result, new Function<Object[], Equipment>() {
                    @Override
                    public Equipment apply(Object[] in) {
                        return (Equipment) in[0];
                    }
                });
            }

            @Override
            public long getCount() {
                return result.size() > 0 ? (Long)result.get(0)[1] : 0;
            }
        };
    }

    @Override
    public List<Equipment> findAll() {
        return (List<Equipment>) session().createQuery("SELECT e FROM Equipment e").list();
    }

    @Override
    public List<Equipment> findByType(Integer[] type) {
        return (List<Equipment>) session().createQuery("SELECT e FROM Equipment e WHERE e.type IN (:type)").setParameterList("type", type).list();
    }
}
