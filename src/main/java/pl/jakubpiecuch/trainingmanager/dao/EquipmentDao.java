package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;

import org.hibernate.HibernateException;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;

public interface EquipmentDao extends CoreDao {
    List<Equipment> findAll();
    List<Equipment> findByType(Integer[] type);
}
