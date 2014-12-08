package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;

import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;

public class EquipmentDaoImpl extends CoreDaoImpl implements EquipmentDao {

    @Override
    public List<Equipment> findAll() {
        return (List<Equipment>) session().createQuery("SELECT e FROM Equipment e").list();
    }

    @Override
    public List<Equipment> findByType(Integer[] type) {
        return (List<Equipment>) session().createQuery("SELECT e FROM Equipment e WHERE e.type IN (:type)").setParameterList("type", type).list();
    }
}
