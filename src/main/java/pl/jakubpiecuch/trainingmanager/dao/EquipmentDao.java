package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.equipment.EquipmentCriteria;

public interface EquipmentDao extends CoreDao<Equipment> {
    PageResult<Equipment> findByCriteria(EquipmentCriteria criteria);
}
