package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import java.util.List;

import static org.junit.Assert.*;

public class DescriptionDaoImplTest extends BaseDAOTestCase {

    @Autowired
    private DescriptionDao descriptionDao;

    @Test
    public void testFindPage() throws Exception {
        descriptionDao.findPage(0,10,new Description.PartyMuscles[] {Description.PartyMuscles.ABDOMINALS});
    }

    @Test
    public void testFindBYCriteria() {
        PageResult<Description> list = descriptionDao.findByCriteria(new DescriptionCriteria().addMuscleRestriction(Description.PartyMuscles.ABDOMINALS).addMuscleRestriction(Description.PartyMuscles.BICEPS).setMaxResultsRestriction(10).setFirstResultRestriction(1));
        assertEquals(1, list.getResult().size());

        list = descriptionDao.findByCriteria(new DescriptionCriteria().addMuscleRestriction(Description.PartyMuscles.ABDOMINALS).addMuscleRestriction(Description.PartyMuscles.BICEPS));
        assertEquals(2, list.getResult().size());

        list = descriptionDao.findByCriteria(new DescriptionCriteria().addMuscleRestriction(Description.PartyMuscles.ABDOMINALS).addForceRestriction(Description.Force.PULL));
        assertEquals(0, list.getResult().size());
    }
}