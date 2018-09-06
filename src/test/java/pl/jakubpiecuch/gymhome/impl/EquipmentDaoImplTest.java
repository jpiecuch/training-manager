package pl.jakubpiecuch.gymhome.impl;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.gymhome.BaseUnitDaoTestCase;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.Equipment;
import pl.jakubpiecuch.gymhome.service.repository.equipment.EquipmentCriteria;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EquipmentDaoImplTest extends BaseUnitDaoTestCase<Equipment> {

    @Autowired
    private RepoDao<Equipment, EquipmentCriteria> equipmentDao;

    @Test
    public void testFindByCriteria() throws Exception {
        PageResult<Equipment> list = equipmentDao.findByCriteria(new EquipmentCriteria("en").addTypeRestriction(Equipment.Type.BAR).setMaxResultsRestriction(10).setFirstResultRestriction(0));
        assertEquals(1, list.getResult().size());
    }

    @Test
    @Override
    public void shouldValidateEntityNotNullConstraints() {
        try {
            equipmentDao.create(getEntity());
        } catch (Exception e) {
            fail();
        }
    }

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList();
    }

    @Override
    protected CoreDao getDao() {
        return equipmentDao;
    }

    @Override
    protected Equipment getEntity() {
        return new Equipment.Bar();
    }
}