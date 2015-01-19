package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import static org.junit.Assert.*;

public class DescriptionDaoImplTest extends BaseDAOTestCase {

    @Autowired
    private DescriptionDao descriptionDao;

    @Test
    public void testFindBYCriteria() {
        PageResult<Description> list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addMuscleRestriction(Description.Muscles.BICEPS).setMaxResultsRestriction(10).setFirstResultRestriction(1));
        assertEquals(2, list.getResult().size());

        list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addMuscleRestriction(Description.Muscles.BICEPS));
        assertEquals(3, list.getResult().size());

        list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addForceRestriction(Description.Force.PULL));
        assertEquals(0, list.getResult().size());
    }
}