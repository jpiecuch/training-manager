package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.BaseIntegrationTestCase;
import pl.jakubpiecuch.trainingmanager.BaseUnitDaoTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DescriptionDaoImplTest extends BaseUnitDaoTestCase {

    @Autowired
    private RepoDao<Description, DescriptionCriteria> descriptionDao;

    @Test
    public void testFindByCriteria() {
        PageResult<Description> list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addMuscleRestriction(Description.Muscles.BICEPS).setMaxResultsRestriction(10).setFirstResultRestriction(1));
        assertEquals(2, list.getResult().size());
        assertNotNull(list.getResult().get(0));
        assertNotNull(list.getCount());

        list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addMuscleRestriction(Description.Muscles.BICEPS));
        assertEquals(3, list.getResult().size());
        assertNotNull(list.getCount());

        list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addForceRestriction(Description.Force.PULL));
        assertEquals(0, list.getResult().size());
        assertEquals(0, list.getCount());
    }
}