package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.EquipmentDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.service.repository.equipment.EquipmentCriteria;

import static org.junit.Assert.*;

public class EquipmentDaoImplTest extends BaseDAOTestCase {

    @Autowired
    private EquipmentDao equipmentDao;

    @Test
    public void testFindByCriteria() throws Exception {
        PageResult<Equipment> list = equipmentDao.findByCriteria(new EquipmentCriteria().addTypeRestriction(Equipment.Type.BAR).setMaxResultsRestriction(10).setFirstResultRestriction(0));
        assertEquals(1, list.getResult().size());


    }
}