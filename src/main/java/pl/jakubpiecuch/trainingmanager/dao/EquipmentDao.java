package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;

public interface EquipmentDao<T extends Equipment> {
    List<T> findByNotInList(List<T> list);
    List<T> findAll();
}
