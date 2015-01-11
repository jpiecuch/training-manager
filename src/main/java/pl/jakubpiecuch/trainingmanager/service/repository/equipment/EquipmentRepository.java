package pl.jakubpiecuch.trainingmanager.service.repository.equipment;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;

/**
 * Created by Rico on 2015-01-02.
 */
public class EquipmentRepository implements Repository<Equipment, EquipmentCriteria> {

    private EquipmentDao equipmentDao;
    private Validator validator;

    @Override
    public PageResult<Equipment> retrieve(EquipmentCriteria criteria) {
        return equipmentDao.findByCriteria(criteria);
    }

    @Override
    public long create(Equipment element) {
        validator.validate(element, new BeanPropertyBindingResult(element, "equipment"));
        equipmentDao.save(element);
        return element.getId();
    }

    @Override
    public void update(Equipment element) {
        validator.validate(element, new BeanPropertyBindingResult(element, "equipment"));
        equipmentDao.save(element);
    }

    @Override
    public void delete(long id) {
        equipmentDao.delete(new Description(id));
    }

    public void setEquipmentDao(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
