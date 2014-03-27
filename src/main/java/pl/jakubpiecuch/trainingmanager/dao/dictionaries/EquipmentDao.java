package pl.jakubpiecuch.trainingmanager.dao.dictionaries;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Equipment;

public interface EquipmentDao<T extends Equipment> {
    List<T> findByNotInList(List<T> list);
    List<T> findAll();
}
