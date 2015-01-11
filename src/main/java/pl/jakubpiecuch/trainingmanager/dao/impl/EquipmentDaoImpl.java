package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.hibernate.Criteria;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
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
        Criteria daoCriteria = session().createCriteria(Equipment.class);
        criteria.fillDaoCriteria(daoCriteria);
        final List list = daoCriteria.list();
        final long count = count();
        return new PageResult<Equipment>() {
            @Override
            public List<Equipment> getResult() {
                return list;
            }

            @Override
            public long getCount() {
                return count;
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
