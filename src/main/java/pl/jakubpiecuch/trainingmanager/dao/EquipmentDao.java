package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.equipment.EquipmentCriteria;

import java.util.List;

public interface EquipmentDao extends CoreDao {
    List<Equipment> findAll();
    List<Equipment> findByType(Integer[] type);
    Long count();
    PageResult<Equipment> findByCriteria(EquipmentCriteria criteria);
}
